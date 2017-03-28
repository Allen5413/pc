package com.allen.service.basic.calculation.impl;

import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecoreproduct.FindProduceLineCoreProductDao;
import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.entity.basic.*;
import com.allen.entity.calculation.PlanDayMaterial;
import com.allen.entity.pojo.producelineuse.ProduceLineUseBean;
import com.allen.service.basic.calculation.CalculationService;
import com.allen.service.basic.factorydate.FindIsWorkByDateService;
import com.allen.service.basic.materialstock.FindStockByFmaterialIdsService;
import com.allen.service.basic.producelineuse.AddProduceLineUseService;
import com.allen.service.basic.producelineuse.DelProduceLineUseService;
import com.allen.service.basic.product.FindProductByPlanService;
import com.allen.service.basic.productinventory.EditProductInventoryService;
import com.allen.service.basic.productinventory.FindProInvByPIdService;
import com.allen.service.basic.productionplan.AddProductionPlanService;
import com.allen.service.basic.productionplan.DelProductionPlanService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 包路径：com.allen.service.basic.calculation.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-07 20:58
 */
@Service
public class CalculationServiceImpl implements CalculationService {
    @Resource
    private FindProductByPlanService findProductByPlanService;
    @Resource
    private FindProduceLineUseDao findProduceLineUseDao;
    @Resource
    private FindProduceLineCoreProductDao findProduceLineCoreProductDao;
    @Resource
    private AddProduceLineUseService addProduceLineUseService;
    @Resource
    private EditProductInventoryService editProductInventoryService;
    @Resource
    private FindProInvByPIdService findProInvByPIdService;
    @Resource
    private FindIsWorkByDateService findIsWorkByDateService;
    @Resource
    private FindStockByFmaterialIdsService findStockByFmaterialIdsService;
    @Resource
    private AddProductionPlanService addProductionPlanService;
    @Resource
    private DelProduceLineUseService delProduceLineUseService;
    @Resource
    private DelProductionPlanService delProductionPlanService;
    private static int SMALL_NUMBER= 4;
    //格式为{客户ID，产品：[{生产日期:PlanDayMaterial},{生产日期:PlanDayMaterial｝]}
    Map<String, LinkedHashMap<String, PlanDayMaterial>> produce = new LinkedHashMap<String, LinkedHashMap<String, PlanDayMaterial>>();
    PlanDayMaterial planDayMaterial = null;
    String start = null;//计划开始时间
    String end = null;//计划结束时间
    BigDecimal minAddTime = new BigDecimal(4);//最低加班时间
    BigDecimal maxWorkTotalTime = new BigDecimal(12);//最高连续工作时间

    Map<Long, MaterialStock> pInvMaps = null;
    Set<String> materialDemandDate = null;
    BigDecimal useQty = null;//生产计划量
    Date productionDate = null;//生产日期
    BigDecimal unitWorkProduct = new BigDecimal(0);//单位时间产能
    BigDecimal actualQty = new BigDecimal(0);//实际产量
    BigDecimal workTime = new BigDecimal(0);//工作时间
    BigDecimal minBatch = new BigDecimal(0);//最小批量

    BigDecimal pRate = new BigDecimal(0);//产品合格率
    BigDecimal minCapacity = new BigDecimal(0);//生产线中最小产能
    BigDecimal workCoreTotalActLast = null;//上一次最小生产量
    BigDecimal eighthCapacity = null;//工作中心8小时产能
    BigDecimal eighthCapacityLast = null;//工作中心8小时产能最后
    BigDecimal minBalanceCapacity = new BigDecimal(0);//生产线的最小剩余产能
    BigDecimal workTimeCapacity = new BigDecimal(0);//工作组班次产量
    BigDecimal workGroupQty = new BigDecimal(0);//班组生产计划产能
    BigDecimal workCoreTotalCapacity = new BigDecimal(0);//工作中心总产能
    BigDecimal workCoreTotalAct = null;//工作中心总产量 没算合格率
    Map<String, LinkedHashMap<String, Map>> workCores = null;//生产线工作中心
    LinkedHashMap<String, Map> classGroups = null;//工作组
    Map workTimeInfo = null;//班次信息
    MaterialStock materialStock = null;//单个产品库存信息
    BigDecimal minChildMaterial = null;//记录产品组成的最小量
    BigDecimal childMaterialCapacity = null;//组成产品的实际产能
    List<String[]> childMaterials = null;//记录产品的下级产品信息
    Long fMaterialId = null;
    //客户id
    long customerId = 0;
    //记录生产计划日期 生产线 工作中心  班组使用情况
    Map<String,ProduceLineUseBean> produceLineUseMap = null;
    ProduceLineUseBean produceLineUseBean = null;

    ProductionPlan productionPlan = null;
    //key 为生产日期，工作组id
    Map<String,BigDecimal> workGroup = null;
    @Override
    @Transactional
    public boolean calculation(String start,String end) throws Exception {
        this.start = start;
        this.end = end;
        Set<String> planCycle = new LinkedHashSet<String>();
        String endNow = start;
        while (true){
            planCycle.add(endNow);
            endNow = DateUtil.afterDay(endNow);
            if(DateUtil.compareDate(end,endNow)<0){
                break;
            }
        }
        //初始化数据
        init();
        //获取生产计划的产品信息
        List<PlanOrder> planOrders = findProductByPlanService.findProductByPlan(start,end);
        //格式为{客户ID，产品：[{生产日期:PlanDayMaterial},{生产日期:PlanDayMaterial｝]},返回产品id集合
        Set<Long> productIds = formatPlan(planOrders);
        //功能根据生产计划产品  获取库存信息 格式为Map<String,ProductInventory> 产品id 库存信息
        pInvMaps = findStockByFmaterialIdsService.find(productIds.toArray(new Long[productIds.size()]));
        //获取生产计划的keys
        Set<String> keys = produce.keySet();
        //生产计划的keys循环产品信息  key有客户id，产品id组成
        for (String key : keys) {
            //获取产品id
            //String[] ids = key.split(",");
            //产品id
            fMaterialId = Long.valueOf(key);
            //客户id
            customerId = 0;
            //产品的每天生产计划信息
            materialDemandDate = produce.get(key).keySet();
            //初始化产品计划信息
            saveProPlan(key);

            calMaterialDateNew(key,null,true);
            //如果产品实际生产还不够  根据产品库存判断
            if(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(new BigDecimal(0))<0){
                 Set<String> unPlanDate = planCycle;
                //查找是否有未安排生产计划的时间的
                for(String demandDate:materialDemandDate){
                    if(!planCycle.add(demandDate)){
                        unPlanDate.remove(demandDate);
                    }
                }
                for(String demandDate:planCycle){
                    if(!findIsWorkByDateService.isWork(demandDate, start, end)){
                        if(!unPlanDate.add(demandDate)){
                            unPlanDate.remove(demandDate);
                            unPlanDate.add(demandDate);
                        }
                    }
                }
                if(unPlanDate.size()>0){
                    materialDemandDate = unPlanDate;
                    LinkedHashMap<String, PlanDayMaterial> playDayMaterials = produce.get(key);
                    PlanDayMaterial planDayMaterialNew = null;
                    for(String demandDate:unPlanDate){
                        planDayMaterialNew = new PlanDayMaterial();
                        planDayMaterialNew.setMaterialId(fMaterialId);
                        planDayMaterialNew.setCustomerId(customerId);
                        planDayMaterialNew.setDemandDate(demandDate);
                        planDayMaterialNew.setUseQty(new BigDecimal(0));
                        planDayMaterialNew.setProductNo(planDayMaterial.getProductNo());
                        planDayMaterialNew.setProductName(planDayMaterial.getProductName());
                        planDayMaterialNew.setProductType(planDayMaterial.getProductType());
                        playDayMaterials.put(demandDate,planDayMaterialNew);
                    }
                    produce.put(key,playDayMaterials);
                    calMaterialDateNew(key,null,false);
                }
            }
        }
        return false;
    }

    /**
     * 功能：计算产品每天的产能
     * @param key
     * @param lessDemandDate 产能不足够那天
     * @param isBack 是否需要回退查找上一步
     * @return
     * @throws Exception
     */
    private void calMaterialDate(String key,String lessDemandDate,boolean isBack) throws Exception {
        //循环生产计划时间
        for (String demandDate : materialDemandDate) {
            //产品某天产能不够 重新循环的时候超过不够那天退出循环，返回还差的产能值
            if(!StringUtil.isEmpty(lessDemandDate)&&DateUtil.compareDate(demandDate,lessDemandDate)>=0){
                return ;
            }
            productionDate = DateUtil.getFormatDate(demandDate, DateUtil.shortDatePattern);
            //产品计划产
            planDayMaterial = produce.get(key).get(demandDate);
            if(!StringUtil.isEmpty(lessDemandDate)){
                useQty = new BigDecimal(0);
            }else{
                //产品计划产量
                useQty = planDayMaterial.getUseQty();
            }
            //获取库存信息
            materialStock = pInvMaps.get(fMaterialId);
            //判断计划生产时间是否正常上班 同时不是加班情况
            if (!findIsWorkByDateService.isWork(demandDate, start, end)&&isBack) {
                if (DateUtil.compareDate(demandDate, start) == 0) {//计划第一天 直接入临时库存
                    materialStock.setFQTY(materialStock.getFQTY().subtract(useQty));
                    pInvMaps.put(fMaterialId, materialStock);
                } else {
                    calMaterialDate(key,demandDate,true);
                }
                continue;
            }
            //库存够用
            if (materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(useQty) > 0) {
                //临时库存这个不需要表存 直接数据结构存
                materialStock.setFQTY(materialStock.getFQTY().subtract(useQty));
                pInvMaps.put(fMaterialId, materialStock);
                //记录当天产品的实际产能
                produce.get(key).get(demandDate).setCapacity(useQty);
                produce.get(key).get(demandDate).setBalanceCapacity(new BigDecimal(0));
                produce.get(key).get(demandDate).setUseQtyStock(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()));
                productionPlan = new ProductionPlan();
                productionPlan.setProductId(fMaterialId);
                productionPlan.setProductionDate(productionDate);
                productionPlan.setDemandNum(useQty);
                productionPlan.setProductionNum(new BigDecimal(0));
                productionPlan.setPlanNum(useQty);
                productionPlan.setGrossNum(new BigDecimal(0));
                productionPlan.setPlanTotalNum(new BigDecimal(0));
                productionPlan.setStockNum(new BigDecimal(0));
                productionPlan.setProductNo(planDayMaterial.getProductNo());
                productionPlan.setProductName(planDayMaterial.getProductName());
                productionPlan.setProductType(planDayMaterial.getProductType());
                productionPlan.setActualProductionNum(new BigDecimal(0));
                addProductionPlanService.addProductionPlan(productionPlan);
                continue;
            } else {
                //
                useQty = useQty.subtract(materialStock.getFQTY()).add(materialStock.getFSAFESTOCK());
                if(useQty.compareTo(new BigDecimal(0))==0){
                    continue;
                }
                //判断产品是否有下级产品
                childMaterials = planDayMaterial.getSelfChilds();
                if (childMaterials != null && childMaterials.size() > 0) {
                    minChildMaterial = new BigDecimal(0);
                    //根据下级产品的产能，计算当前产品实际的产能数量
                    for (String[] childMaterial : childMaterials) {
                        //根据下级产能计算出当前级别的产品最多能生产好多个
                        childMaterialCapacity = produce.get(customerId + "," + childMaterial[0]).get(demandDate)
                                .getUseQtyStock().divide(new BigDecimal(childMaterial[1]));
                        if (minChildMaterial.intValue() == 0) {
                            minChildMaterial = childMaterialCapacity;
                        } else {
                            minChildMaterial = minChildMaterial.compareTo(childMaterialCapacity) > 0 ? childMaterialCapacity : minChildMaterial;
                        }
                    }
                    //生产产品计划量 与下级产品的最小数量对比
                    useQty = useQty.compareTo(minChildMaterial) > 0 ? useQty : minChildMaterial;
                }
            }
            //获取产品的生产线 生产中心 工作租 班次信息
            List<Map> produceLines = findProduceLineUseDao.findUnUserProduceLine(fMaterialId);
            //格式化分组生产线  工作中心  工作组 班次信息
            Map<String, Map<String, LinkedHashMap<String, Map>>> pLines = formatProductLine(produceLines);
            Set<String> pLineIds = pLines.keySet();//生产线id
            Set<String> workCoreIds = null;//生产中心id
            Set<String> classGroupIds = null;//班组信息
            workCores = null;//生产线工作中心
            classGroups = null;//工作组
            workTimeInfo = null;//班次信息
            boolean lineFlag = true;
            boolean coreFlag = true;
            BigDecimal lineTotalCapacity = new BigDecimal(0);//产品当天产能
            //循环生产线信息
            for (String pLineId : pLineIds) {
                workCores = pLines.get(pLineId);//生产中心
                workCoreIds = workCores.keySet();
                minCapacity = new BigDecimal(0);//生产线最小产能
                workCoreTotalActLast = new BigDecimal(0);
                eighthCapacityLast = new BigDecimal(0);
                eighthCapacity = new BigDecimal(0);
                minBalanceCapacity = new BigDecimal(Integer.MAX_VALUE);//生产线最小剩余产能
                //循环生产中心
                for (String workCoreId : workCoreIds) {
                    classGroups = workCores.get(workCoreId);//班组信息
                    classGroupIds = classGroups.keySet();
                    //实际产量
                    actualQty = new BigDecimal(0);
                    workCoreTotalCapacity = new BigDecimal(0);
                    workCoreTotalAct = new BigDecimal(0);
                    coreFlag = true;
                    //此循环是为了应对正常工作模式 时间无法满足产能的情况 需要加班
                    //跳出循环有两种情况 1是产能够用  2是所有班组都已经满负荷上班（班组超过工作时间超过16小时）
                    //加班是4个小时为一个单位计算
                    boolean isAddWorkTime = false;//是否加班
                    int index = 0;
                    while (true) {
                        index = 0;
                        //循环班组
                        for (String classGroupId : classGroupIds) {
                            index++;
                            workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                            //生产线工作中心班组使用情况
                            String produceLineUseKey = demandDate+","+pLineId+","+workCoreId+","+classGroupId;
                            if(produceLineUseMap.get(produceLineUseKey)==null){
                                produceLineUseBean = new ProduceLineUseBean();
                            }else{
                                produceLineUseBean = produceLineUseMap.get(produceLineUseKey);
                            }
                            //生产线 工作中心 班组 已经满负荷生产 同时没有剩余产能
                            if(produceLineUseBean.isFullWork()
                                    &&produceLineUseBean.getBalanceCapacity().compareTo(new BigDecimal(0))<=0){
                                if(index == classGroupIds.size()){
                                    coreFlag = false;
                                }
                                continue;
                            }
                            String workTimeId = workTimeInfo.get("work_time_id").toString();
                            //生产中心产品合格率
                            pRate = new BigDecimal(workTimeInfo.get("qualified_rate").toString());
                            //当前班组计划产能  工作中心总计划产能减去 其他班组已经生产的产能
                            workGroupQty = (useQty.subtract(workCoreTotalCapacity)).divide(pRate,2,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
                            //最小批量
                            minBatch = new BigDecimal(workTimeInfo.get("min_batch").toString());
                            //计划产能除以最小批量  不整除时候
                            if (workGroupQty.divideAndRemainder(minBatch)[1].compareTo(new BigDecimal(0)) != 0) {
                                //不整除 加1 乘以最小批量
                                BigDecimal balance = workGroupQty.divideAndRemainder(minBatch)[0];
                                BigDecimal balanceNew = balance.add(new BigDecimal(1));
                                workGroupQty = balanceNew.multiply(minBatch);
                            }
                            //单位时间产能，数据库存的是秒的产能
                            unitWorkProduct = new BigDecimal(workTimeInfo.get("unit_time_capacity").toString()).divide(new BigDecimal(3600));
                            //判断上次生产产品与这次生产产品是否一致
                            if(produceLineUseBean.getLastFMaterialId()==fMaterialId){
                                //表示工作中心生产剩余产能够用
                                if(produceLineUseBean.getBalanceCapacity().compareTo(workGroupQty)>=0){
                                    //重新计算剩余产能
                                    produceLineUseBean.setBalanceCapacity(
                                            produceLineUseBean.getBalanceCapacity().subtract(workGroupQty));
                                    //重新计算剩余时间
                                    produceLineUseBean.setBalanceWorkTime(produceLineUseBean.getBalanceWorkTime()
                                            .subtract(workGroupQty.divide(unitWorkProduct,SMALL_NUMBER,BigDecimal.ROUND_HALF_EVEN) ));
                                    produceLineUseMap.put(produceLineUseKey,produceLineUseBean);
                                    saveProduceLineUse(fMaterialId,workCoreId,pLineId,0,productionDate,workTimeId,
                                            classGroupId,customerId,produceLineUseBean.getAddWorkTime(),
                                            produceLineUseBean.getBalanceWorkTime(),produceLineUseBean.getBalanceCapacity(),
                                            workGroupQty,workGroupQty);
                                    continue;
                                }else {
                                    //重新计算剩余产能
                                    produceLineUseBean.setBalanceCapacity(new BigDecimal(0));
                                }
                            }else{//不是同一个产品 不能使用剩余产能，需要重新计算
                                //重新计算剩余产能
                                produceLineUseBean.setBalanceCapacity(new BigDecimal(0));
                            }
                            //第一次使用工作中心  班组
                            if(produceLineUseMap.get(produceLineUseKey)==null){
                                //计算班次工作时间
                                workTime = getWorkTime(workTimeInfo.get("begin_time").toString(),
                                        workTimeInfo.get("end_time").toString());
                                eighthCapacity = eighthCapacity.add(workTime.multiply(unitWorkProduct));
                                produceLineUseBean.setWorkTotalTime(workTime);
                            }else{
                                //正常上班
                                if(!isAddWorkTime){
                                    workTime = produceLineUseBean.getBalanceWorkTime();
                                    if(workTime.compareTo(new BigDecimal(0))==0){
                                        continue;
                                    }
                                }else{
                                    //加班
                                    produceLineUseBean.setFullWork(produceLineUseBean.getWorkTotalTime()
                                            .add(minAddTime).compareTo(maxWorkTotalTime)>=0);
                                    //如果加班时超过了总时间12小时 取相差时间为工作时间   如果小于12小时取最小工作时间为准
                                    workTime = produceLineUseBean.isFullWork()? maxWorkTotalTime
                                            .subtract(produceLineUseBean.getWorkTotalTime()):minAddTime;
                                    produceLineUseBean.setAddWorkTime(produceLineUseBean.getAddWorkTime().add(workTime));
                                    produceLineUseBean.setWorkTotalTime(produceLineUseBean.getWorkTotalTime().add(workTime));
                                }
                            }
                            //判断工作组是否超时使用
                            BigDecimal workGroupTime = workGroup.get(demandDate+","+classGroupId);
                            if(workGroupTime!=null&&
                                    workGroupTime.compareTo(maxWorkTotalTime)>=0){
                                continue;
                            }else{
                                workGroupTime = workGroupTime==null?new BigDecimal(0):workGroupTime;
                                if((workGroupTime.add(workTime)).compareTo(maxWorkTotalTime)>=0){
                                    workTime = maxWorkTotalTime.subtract(workGroupTime);
                                }
                                workGroupTime = workGroupTime.add(workTime);
                            }
                            //当前生产的产品id
                            produceLineUseBean.setLastFMaterialId(fMaterialId);
                            //计算方法 生产中心合格率*班组单位时间产能*班次工作时间
                            workTimeCapacity = unitWorkProduct.multiply(workTime);
                            //班组产能  大于班组计划产能
                            if(workTimeCapacity.compareTo(workGroupQty)>=0){
                                //剩余产能
                                produceLineUseBean.setBalanceCapacity(workTimeCapacity.subtract(workGroupQty));
                                //剩余时间  剩余产能除单位时间产能
                                produceLineUseBean.setBalanceWorkTime(produceLineUseBean.getBalanceCapacity()
                                        .divide(unitWorkProduct,SMALL_NUMBER,BigDecimal.ROUND_HALF_EVEN));
                                actualQty = workGroupQty.multiply(pRate).divide(new BigDecimal(100));
                                workCoreTotalCapacity = workCoreTotalCapacity.add(workGroupQty.multiply(pRate).divide(new BigDecimal(100)));
                                workCoreTotalAct = workCoreTotalAct.add(workGroupQty);
                                workGroup.put(demandDate+","+classGroupId,workGroupTime.subtract(produceLineUseBean.getBalanceWorkTime()));
                                coreFlag = false;
                            }else{
                                BigDecimal workTimeCapacityNew = (workTimeCapacity.divide(minBatch,0, RoundingMode.FLOOR)).multiply(minBatch);
                                produceLineUseBean.setBalanceCapacity(workTimeCapacityNew.subtract(workTimeCapacity));
                                produceLineUseBean.setBalanceWorkTime(produceLineUseBean.getBalanceCapacity()
                                        .divide(unitWorkProduct,SMALL_NUMBER,BigDecimal.ROUND_HALF_EVEN));
                                actualQty = workTimeCapacityNew.multiply(pRate).divide(new BigDecimal(100));
                                workGroupQty = workTimeCapacityNew.multiply(pRate).divide(new BigDecimal(100));
                                workCoreTotalAct = workCoreTotalAct.add(workTimeCapacityNew);
                                workCoreTotalCapacity = workCoreTotalCapacity.add(workTimeCapacityNew.multiply(pRate).divide(new BigDecimal(100)));
                                workGroup.put(demandDate+","+classGroupId,workGroupTime);
                            }
                            produceLineUseMap.put(produceLineUseKey,produceLineUseBean);
                            saveProduceLineUse(fMaterialId,workCoreId,pLineId,produceLineUseBean.isFullWork()?1:0,productionDate,workTimeId,
                                    classGroupId,customerId,produceLineUseBean.getAddWorkTime(),
                                    produceLineUseBean.getBalanceWorkTime(),produceLineUseBean.getBalanceCapacity(),
                                    workGroupQty,actualQty);
                            if (!coreFlag) {
                                break;
                            }
                        }
                        //表示产量已够或者满负荷生产了
                        if (!coreFlag) {
                            break;
                        } else {//如果共生产中心所有班组正常情况不能满足产能  需要加班
                            isAddWorkTime = true;
                        }
                    }
                    //计算生产线中工作中心最小产能
                    if (minCapacity.compareTo(new BigDecimal(0)) == 0) {
                        minCapacity = workCoreTotalCapacity;
                        workCoreTotalActLast = workCoreTotalAct;
                        eighthCapacityLast = eighthCapacity;
                    } else {
                        boolean isMin = minCapacity.compareTo(workCoreTotalCapacity) > 0;
                        minCapacity = isMin ? workCoreTotalCapacity : minCapacity;
                        workCoreTotalActLast = isMin ? workCoreTotalAct : workCoreTotalActLast;
                        eighthCapacityLast = isMin ? eighthCapacity : eighthCapacityLast;
                    }
                }
                //生产线总产能
                lineTotalCapacity = lineTotalCapacity.add(minCapacity);
                //生产线满足 跳出产能计算
                if (lineTotalCapacity.compareTo(useQty) > 0) {
                    lineFlag = false;
                    break;
                }
            }
            //记录当天产品的实际产能
            produce.get(key).get(demandDate).setCapacity(lineTotalCapacity);
            //如果库存有多余的 当天实际有的产品数量要加上库存
            if(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(new BigDecimal(0))>=0){
                produce.get(key).get(demandDate).setUseQtyStock(lineTotalCapacity.add(
                        materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK())));
            }else{
                //库存没有多余的  当天实际产品数量以生产量为准
                produce.get(key).get(demandDate).setUseQtyStock(lineTotalCapacity);
            }
            materialStock.setFQTY(lineTotalCapacity.subtract(useQty));
            pInvMaps.put(fMaterialId, materialStock);
            //所有生产线满负荷生产  不能满足生产计划
            productionPlan = new ProductionPlan();
            productionPlan.setProductId(fMaterialId);
            productionPlan.setProductionDate(productionDate);
            productionPlan.setDemandNum(useQty);
            productionPlan.setProductionNum(lineTotalCapacity);
            productionPlan.setPlanNum(planDayMaterial.getUseQty());
            productionPlan.setGrossNum(new BigDecimal(0));
            productionPlan.setPlanTotalNum(new BigDecimal(0));
            productionPlan.setStockNum(new BigDecimal(0));
            productionPlan.setProductType(planDayMaterial.getProductType());
            productionPlan.setProductNo(planDayMaterial.getProductNo());
            productionPlan.setProductName(planDayMaterial.getProductName());
            productionPlan.setActualProductionNum(workCoreTotalActLast);
            productionPlan.setCapacity(eighthCapacityLast);
            addProductionPlanService.addProductionPlan(productionPlan);
            if (lineTotalCapacity.compareTo(useQty) < 0) {
                if(isBack) {
                    calMaterialDate(key, demandDate, true);
                }
            }
        }
    }

    /**
     * 功能:获取最小工作时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal getWorkTime(String startTime, String endTime) {
        Date start = DateUtil.getFormatDate(startTime, DateUtil.longDatePattern);
        Date end = DateUtil.getFormatDate(endTime, DateUtil.longDatePattern);
        return new BigDecimal((float) (end.getTime() - start.getTime()) / 3600 / 1000).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 功能：当天产能不够时，查找该产品的前一天的生产计划日期
     *
     * @param demandDate 不够产能的当天时间
     * @param start      计划安排的开始时间
     * @param end        计划安排结束时间
     * @param plans      产品的生产安排计划
     * @return
     */
    private String getBeforePlanDate(String demandDate, String start, String end,
                                     LinkedHashMap<String, PlanDayMaterial> plans) throws Exception {
        String beforeDate = DateUtil.beforeDay(demandDate);
        if (DateUtil.compareDate(beforeDate, start) <= 0) {//代表前一天已经是最开始时间不能再找
            return start;
        }
        if (plans.get(beforeDate) == null) {//前一天没有安排工作计划
            return getBeforePlanDate(beforeDate, start, end, plans);
        }
        return beforeDate;
    }

    /**
     * 功能：获取产品库存信息
     *
     * @return
     */
    private Map<String, ProductInventory> getProductInv() {
        Map<String, ProductInventory> pInvMaps = new HashMap<String, ProductInventory>();
        ProductInventory pInvObj = new ProductInventory();
        pInvObj.setProductNum(new BigDecimal(200));//产品库存
        pInvObj.setSafe(new BigDecimal(200));//安全库存
        pInvObj.setMax(new BigDecimal(400));//最大库存
        pInvObj.setMin(new BigDecimal(100));//最小库存
        pInvMaps.put("114937", pInvObj);
        return pInvMaps;
    }
    //格式化生产计划
    private  Set<Long> formatPlan(List<PlanOrder> planOrders){
        String lastProductId = "-1";
        LinkedHashMap<String, PlanDayMaterial> demandDatePlanQty = null;//每日计划产量
        String produceDate = null;
        Set<Long> productIds = new HashSet<Long>();
        for (PlanOrder planOrder : planOrders) {
            //获取每个订单的产品信息
            List<Map> products = planOrder.getSortProducts();
            //生产日期
            produceDate = DateUtil.getFormattedString(planOrder.getFDEMANDDATE(), DateUtil.shortDatePattern);
            for (Map product : products) {
                if (product.get("FCATEGORYID") != null && !"239".equals(product.get("FCATEGORYID").toString()) &&
                        !"241".equals(product.get("FCATEGORYID").toString())) {//产品不是自制半成品 产成品的不计算
                    continue;
                }
                //planOrder.getFCUSTID() + "," +
                productIds.add(Long.valueOf(product.get("FMATERIALID").toString()));
                if ("-1".equals(lastProductId) || produce.get(product.get("FMATERIALID").toString()) == null) {
                    demandDatePlanQty = new LinkedHashMap<String, PlanDayMaterial>();
                    produce.put(product.get("FMATERIALID").toString(), demandDatePlanQty);
                } else {
                    demandDatePlanQty = produce.get(product.get("FMATERIALID").toString());
                }
                if (demandDatePlanQty.get(produceDate) == null) {
                    planDayMaterial = new PlanDayMaterial();
                    //计划产能
                    planDayMaterial.setUseQty(planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString())));
                    planDayMaterial.setCustomerId(Long.valueOf(planOrder.getFCUSTID()));
                    planDayMaterial.setDemandDate(produceDate);
                    planDayMaterial.setProductName(product.get("FNAME")==null?planOrder.getFNAME():product.get("FNAME").toString());
                    planDayMaterial.setProductNo(product.get("FNUMBER")==null?planOrder.getFNUMBER():product.get("FNUMBER").toString());
                    planDayMaterial.setProductType(product.get("FCATEGORYID")==null?planOrder.getFCATEGORYID():product.get("FCATEGORYID").toString());
                    if (product.get("childs") != null) {
                        planDayMaterial.setChilds((ArrayList) product.get("childs"));
                    }
                    demandDatePlanQty.put(produceDate, planDayMaterial);
                } else {
                    //同一产品 同一计划生产日期累加
                    planDayMaterial = demandDatePlanQty.get(produceDate);
                    BigDecimal useQty = planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString()));
                    planDayMaterial.setUseQty(useQty.add(planDayMaterial.getUseQty()));
                }
                lastProductId = product.get("FMATERIALID").toString();
            }
        }
        return  productIds;
    }
    //格式化生产线信息
    private Map<String, Map<String, LinkedHashMap<String, Map>>> formatProductLine(List<Map> produceLines){
        String lastPLine = null;//生产线
        String lastWorkCore = null;//工作中心
        String lastClassGroup = null;//工作组
        Map<String, Map<String, LinkedHashMap<String, Map>>> pLines =
                new LinkedHashMap<String, Map<String, LinkedHashMap<String, Map>>>();
        for (Map pLine : produceLines) {
            //生产线不同时候
            if (lastPLine == null || !(pLine.get("produce_line_id").toString()+"!_!"+pLine.get("extend").toString()).equals(lastPLine)) {
                workCores = new HashMap<String, LinkedHashMap<String, Map>>();
                pLines.put(pLine.get("produce_line_id").toString()+"!_!"+pLine.get("extend").toString(), workCores);
            }
            //工作中心不同
            if (lastPLine == null || !(pLine.get("produce_line_id").toString()+"!_!"+pLine.get("extend").toString()).equals(lastPLine) ||
                    !pLine.get("work_core_id").toString().equals(lastWorkCore)) {
                classGroups = new LinkedHashMap<String, Map>();
                workCores.put(pLine.get("work_core_id").toString(), classGroups);
            }
            //工作组不同
            if (lastPLine == null || !(pLine.get("produce_line_id").toString()+"!_!"+pLine.get("extend").toString()).equals(lastPLine) ||
                    !pLine.get("work_core_id").toString().equals(lastWorkCore) ||
                    !pLine.get("class_group_id").toString().equals(lastClassGroup)) {
                workTimeInfo = new LinkedHashMap<String, Map>();
                classGroups.put(pLine.get("class_group_id").toString(), workTimeInfo);
            }
            classGroups.put(pLine.get("class_group_id").toString(), pLine);
            lastPLine = pLine.get("produce_line_id").toString()+"!_!"+pLine.get("extend").toString();
            lastWorkCore = pLine.get("work_core_id").toString();
            lastClassGroup = pLine.get("class_group_id").toString();
        }
        return pLines;
    }

    /**
     * 保存产品使用生产情况
     * @param fMaterialId
     * @param workCoreId
     * @param pLineId
     * @param isFull
     * @param productionDate
     * @param workTimeId
     * @param classGroupId
     * @param customerId
     * @param addWorkTime
     * @param balanceTime
     * @param balanceCapacity
     * @param planQuantity
     * @param capacity
     */
    private void saveProduceLineUse(Long fMaterialId,String workCoreId,String pLineId,int isFull,Date productionDate,
                                    String workTimeId,String classGroupId,Long customerId,BigDecimal addWorkTime,
                                    BigDecimal balanceTime,BigDecimal balanceCapacity,BigDecimal planQuantity,
                                    BigDecimal capacity){
        ProduceLineUse produceLineUse = new ProduceLineUse();
        produceLineUse.setIsFull(0);
        produceLineUse.setWorkCoreId(Long.valueOf(workCoreId));
        //产品id
        produceLineUse.setProductId(fMaterialId);
        //生产线id
        produceLineUse.setProduceLineId(Long.valueOf(pLineId));
        //生产日期
        produceLineUse.setProductionDate(productionDate);
        //班次id
        produceLineUse.setWorkTimeId(Long.valueOf(workTimeId));
        //班组id
        produceLineUse.setWorkTeamId(Long.valueOf(classGroupId));
        //客户id
        produceLineUse.setCustomerId(customerId);
        //加班时间
        produceLineUse.setAddTime(addWorkTime);
        //剩余时间
        produceLineUse.setBalanceTime(balanceTime);
        //剩余产能
        produceLineUse.setBalanceCapacity(balanceCapacity);
        //计划产量
        produceLineUse.setPlanQuantity(planQuantity);
        //实际产量
        produceLineUse.setCapacity(capacity);
        addProduceLineUseService.addProduceLineUse(produceLineUse);
    }

    private void init(){
        //删除计划时间已经存在的数据
        delProductionPlanService.delProductionPlan(start,end,0);
        delProduceLineUseService.delProduceLineUse(start,end,0);
        produce = new LinkedHashMap<String, LinkedHashMap<String, PlanDayMaterial>>();
        planDayMaterial = null;
        pInvMaps = null;
        materialDemandDate = null;
        useQty = null;//生产计划量
        productionDate = null;//生产日期
        eighthCapacity = null;//8小时产能
        unitWorkProduct = new BigDecimal(0);//单位时间产能
        actualQty = new BigDecimal(0);//实际产量
        workTime = new BigDecimal(0);//工作时间
        minBatch = new BigDecimal(0);//最小批量

        pRate = new BigDecimal(0);//产品合格率
        minCapacity = new BigDecimal(0);//生产线中最小产能
        minBalanceCapacity = new BigDecimal(0);//生产线的最小剩余产能
        workCoreTotalActLast = new BigDecimal(0);//最近一次生产
        workTimeCapacity = new BigDecimal(0);//工作组班次产量
        workGroupQty = new BigDecimal(0);//班组生产计划产能
        workCoreTotalCapacity = new BigDecimal(0);//工作中心总产能
        workCoreTotalAct =  new BigDecimal(0);;//工作中心总产量 没算合格率
        workCores = null;//生产线工作中心
        classGroups = null;//工作组
        workTimeInfo = null;//班次信息
        materialStock = null;//单个产品库存信息
        minChildMaterial = null;//记录产品组成的最小量
        childMaterialCapacity = null;//组成产品的实际产能
        childMaterials = null;//记录产品的下级产品信息
        fMaterialId = null;
        //客户id
        customerId = 0;
        //记录生产计划日期 生产线 工作中心  班组使用情况
        produceLineUseMap = new HashMap<String, ProduceLineUseBean>();;
        produceLineUseBean = null;
        productionPlan = null;
        workGroup = new HashMap<String, BigDecimal>();
    }
    //初始化生产计划产能信息
    public void saveProPlan(String key){
        int first = 0;
        //循环生产计划时间
        for (String demandDate:materialDemandDate) {
            planDayMaterial = produce.get(key).get(demandDate);
            //获取库存信息
            materialStock = pInvMaps.get(fMaterialId);
            productionPlan = new ProductionPlan();
            productionPlan.setProductId(fMaterialId);
            productionPlan.setProductionDate(DateUtil.getFormatDate(demandDate,DateUtil.shortDatePattern));
            productionPlan.setDemandNum(new BigDecimal(0));
            productionPlan.setProductionNum(new BigDecimal(0));
            productionPlan.setPlanNum(planDayMaterial.getUseQty());
            productionPlan.setGrossNum(new BigDecimal(0));
            productionPlan.setPlanTotalNum(planDayMaterial.getUseQty());
            productionPlan.setStockNum(first==0?materialStock.getFQTY():new BigDecimal(0));
            if(first==0){
                materialStock.setFQTY(materialStock.getFSAFESTOCK());
                pInvMaps.put(fMaterialId,materialStock);
            }
            productionPlan.setProductType(planDayMaterial.getProductType());
            productionPlan.setProductNo(planDayMaterial.getProductNo());
            productionPlan.setProductName(planDayMaterial.getProductName());
            addProductionPlanService.addProductionPlan(productionPlan);
            first++;
        }
    }
    /**
     * 功能：计算产品每天的产能
     * @param key
     * @param lessDemandDate 产能不足够那天
     * @param isBack 是否需要回退查找上一步
     * @return
     * @throws Exception
     */
    private void calMaterialDateNew(String key,String lessDemandDate,boolean isBack) throws Exception {
        //循环生产计划时间
        for (String demandDate : materialDemandDate) {
            //产品某天产能不够 重新循环的时候超过不够那天退出循环，返回还差的产能值
            if(!StringUtil.isEmpty(lessDemandDate)&&DateUtil.compareDate(demandDate,lessDemandDate)>=0){
                return ;
            }
            //产品计划产
            planDayMaterial = produce.get(key).get(demandDate);
            if(planDayMaterial.isFullWork()){
                continue;
            }
            productionDate = DateUtil.getFormatDate(demandDate, DateUtil.shortDatePattern);
            //产品计划产量
            useQty = planDayMaterial.getUseQty();
            //获取库存信息
            materialStock = pInvMaps.get(fMaterialId);
            //判断计划生产时间是否正常上班 同时不是加班情况
            if (!findIsWorkByDateService.isWork(demandDate, start, end)&&isBack) {
                materialStock.setFQTY(materialStock.getFQTY().subtract(useQty));
                pInvMaps.put(fMaterialId, materialStock);
                if (DateUtil.compareDate(demandDate, start) != 0) {//不是计划第一天
                    calMaterialDateNew(key,demandDate,true);
                }
                continue;
            }
            //库存够用
            if (materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(useQty) > 0) {
                //临时库存这个不需要表存 直接数据结构存
                materialStock.setFQTY(materialStock.getFQTY().subtract(useQty));
                pInvMaps.put(fMaterialId, materialStock);
                //记录当天产品的实际产能
                produce.get(key).get(demandDate).setCapacity(useQty);
                produce.get(key).get(demandDate).setBalanceCapacity(new BigDecimal(0));
                produce.get(key).get(demandDate).setUseQtyStock(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()));
                productionPlan = new ProductionPlan();
                productionPlan.setProductId(fMaterialId);
                productionPlan.setProductionDate(productionDate);
                productionPlan.setDemandNum(useQty);
                productionPlan.setProductionNum(new BigDecimal(0));
                productionPlan.setPlanNum(useQty);
                productionPlan.setGrossNum(new BigDecimal(0));
                productionPlan.setPlanTotalNum(new BigDecimal(0));
                productionPlan.setStockNum(new BigDecimal(0));
                productionPlan.setProductNo(planDayMaterial.getProductNo());
                productionPlan.setProductName(planDayMaterial.getProductName());
                productionPlan.setProductType(planDayMaterial.getProductType());
                productionPlan.setActualProductionNum(new BigDecimal(0));
                addProductionPlanService.addProductionPlan(productionPlan);
                continue;
            } else {
                useQty = useQty.subtract(materialStock.getFQTY()).add(materialStock.getFSAFESTOCK());
                if(isBack){
                    useQty = useQty.add(planDayMaterial.getStorageNum());
                }
                if(useQty.compareTo(new BigDecimal(0))==0){
                    continue;
                }
                //判断产品是否有下级产品
                childMaterials = planDayMaterial.getSelfChilds();
                if (childMaterials != null && childMaterials.size() > 0) {
                    minChildMaterial = new BigDecimal(0);
                    //根据下级产品的产能，计算当前产品实际的产能数量
                    for (String[] childMaterial : childMaterials) {
                        //根据下级产能计算出当前级别的产品最多能生产好多个
                        childMaterialCapacity = produce.get(customerId + "," + childMaterial[0]).get(demandDate)
                                .getUseQtyStock().divide(new BigDecimal(childMaterial[1]));
                        if (minChildMaterial.intValue() == 0) {
                            minChildMaterial = childMaterialCapacity;
                        } else {
                            minChildMaterial = minChildMaterial.compareTo(childMaterialCapacity) > 0 ? childMaterialCapacity : minChildMaterial;
                        }
                    }
                    //生产产品计划量 与下级产品的最小数量对比
                    useQty = useQty.compareTo(minChildMaterial) > 0 ? useQty : minChildMaterial;
                }
            }
            //获取产品的生产线 生产中心 工作租 班次信息
            List<Map> produceLines = findProduceLineUseDao.findUnUserProduceLine(fMaterialId);
            //格式化分组生产线  工作中心  工作组 班次信息
            Map<String, Map<String, LinkedHashMap<String, Map>>> pLines = formatProductLine(produceLines);
            Set<String> pLineIds = pLines.keySet();//生产线id

            workCores = null;//生产线工作中心
            classGroups = null;//工作组
            workTimeInfo = null;//班次信息
            boolean lineFlag = true;
            boolean coreFlag = true;
            BigDecimal lineTotalCapacity = new BigDecimal(0);//产品当天产能 实际产量
            BigDecimal eighthCapacity = new BigDecimal(0);
            BigDecimal minBatch = new BigDecimal(0);
            BigDecimal minPro = new BigDecimal(0);
            BigDecimal rate = new BigDecimal(0);
            BigDecimal actualProductionNum = new BigDecimal(0);
            //循环生产线信息
            for (String pLineInfo : pLineIds) {
                String[] pLineInfoArr = pLineInfo.split("!_!");
                String pLineId = pLineInfoArr[0];
                JSONObject jsonObject = JSON.parseObject(pLineInfoArr[1]);
                JSONObject extendInfo = JSON.parseObject(jsonObject.get(fMaterialId+"").toString());
                minPro = new BigDecimal(extendInfo.get("minPro").toString());
                minBatch = new BigDecimal(extendInfo.get("minBatch").toString());
                rate = new BigDecimal(extendInfo.get("rate").toString());
                eighthCapacity = eighthCapacity.add(new BigDecimal(extendInfo.get("eighthPro").toString()));
                BigDecimal lineUseQty = new BigDecimal(0);
                lineUseQty = useQty.divide(rate,0,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
                if(lineUseQty.compareTo(minPro)>=0){//满负荷生产
                    actualProductionNum = actualProductionNum.add(minPro);
                    lineTotalCapacity = lineTotalCapacity.add(minPro.multiply(rate).divide(new BigDecimal(100)));
                    //计算 排班信息
                    calScheduling(minPro,pLines.get(pLineInfo),demandDate,pLineId);
                    break;
                }else{
                    //计划产能除以最小批量  不整除时候
                    if (lineUseQty.divideAndRemainder(minBatch)[1].compareTo(new BigDecimal(0)) != 0) {
                        //不整除 加1 乘以最小批量
                        BigDecimal balance = lineUseQty.divideAndRemainder(minBatch)[0];
                        BigDecimal balanceNew = balance.add(new BigDecimal(1));
                        lineUseQty = balanceNew.multiply(minBatch);
                    }
                    actualProductionNum = actualProductionNum.add(lineUseQty);
                    lineTotalCapacity = lineTotalCapacity.add(lineUseQty.multiply(rate).divide(new BigDecimal(100)));
                    //计算 排班信息
                    calScheduling(lineUseQty,pLines.get(pLineInfo),demandDate,pLineId);
                }
            }
            if(lineTotalCapacity.compareTo(useQty)<=0){//生产线的总数量小于计划量
                planDayMaterial.setFullWork(true);
            }
            //记录当天产品的实际产能
            produce.get(key).get(demandDate).setCapacity(lineTotalCapacity);
            //如果库存有多余的 当天实际有的产品数量要加上库存
            if(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(new BigDecimal(0))>=0){
                produce.get(key).get(demandDate).setUseQtyStock(lineTotalCapacity.add(
                        materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK())));
            }else{
                //库存没有多余的  当天实际产品数量以生产量为准
                produce.get(key).get(demandDate).setUseQtyStock(lineTotalCapacity);
            }
            planDayMaterial.setStorageNum(lineTotalCapacity.subtract(useQty));
            materialStock.setFQTY(lineTotalCapacity.subtract(useQty));
            pInvMaps.put(fMaterialId, materialStock);
            //所有生产线满负荷生产  不能满足生产计划
            productionPlan = new ProductionPlan();
            productionPlan.setProductId(fMaterialId);
            productionPlan.setProductionDate(productionDate);
            productionPlan.setDemandNum(useQty);
            productionPlan.setProductionNum(lineTotalCapacity);
            productionPlan.setPlanNum(planDayMaterial.getUseQty());
            productionPlan.setGrossNum(new BigDecimal(0));
            productionPlan.setPlanTotalNum(new BigDecimal(0));
            productionPlan.setStockNum(new BigDecimal(0));
            productionPlan.setProductType(planDayMaterial.getProductType());
            productionPlan.setProductNo(planDayMaterial.getProductNo());
            productionPlan.setProductName(planDayMaterial.getProductName());
            productionPlan.setActualProductionNum(actualProductionNum);
            productionPlan.setCapacity(eighthCapacity);
            addProductionPlanService.addProductionPlan(productionPlan);
            if (lineTotalCapacity.compareTo(useQty) < 0) {
                if(isBack) {
                    calMaterialDateNew(key, demandDate, true);
                }
            }
        }
    }
    /**
     * 计算排班信息
     * @param proNum
     * @param workCores
     */
    public void calScheduling(BigDecimal proNum,Map<String, LinkedHashMap<String, Map>> workCores,
                           String demandDate,String pLineId){
        Set<String> workCoreIds =  workCores.keySet();//生产中心id
        Set<String> classGroupIds = null;//班组信息
        boolean coreFlag = true;
        //循环生产中心
        for (String workCoreId : workCoreIds) {
            classGroups = workCores.get(workCoreId);//班组信息
            classGroupIds = classGroups.keySet();
            //此循环是为了应对正常工作模式 时间无法满足产能的情况 需要加班
            //跳出循环有两种情况 1是产能够用  2是所有班组都已经满负荷上班（班组超过工作时间超过16小时）
            //加班是4个小时为一个单位计算
            boolean isAddWorkTime = false;//是否加班
            int index = 0;
            coreFlag = true;
            while (true) {
                index = 0;
                //循环班组
                for (String classGroupId : classGroupIds) {
                    index++;
                    workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                    //生产线工作中心班组使用情况
                    String produceLineUseKey = demandDate+","+pLineId+","+workCoreId+","+classGroupId;
                    if(produceLineUseMap.get(produceLineUseKey)==null){
                        produceLineUseBean = new ProduceLineUseBean();
                    }else{
                        produceLineUseBean = produceLineUseMap.get(produceLineUseKey);
                    }
                    //生产线 工作中心 班组 已经满负荷生产
                    if(produceLineUseBean.isFullWork()){
                        if(index == classGroupIds.size()){
                            coreFlag = false;
                        }
                        continue;
                    }
                    String workTimeId = workTimeInfo.get("work_time_id").toString();
                    //生产中心产品合格率
                    pRate = new BigDecimal(workTimeInfo.get("qualified_rate").toString());
                    //当前班组计划产能  工作中心总计划产能减去 其他班组已经生产的产能
                    workGroupQty = (proNum.subtract(workCoreTotalCapacity)).divide(pRate,2,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100));
                    //最小批量
                    minBatch = new BigDecimal(workTimeInfo.get("min_batch").toString());
                    //计划产能除以最小批量  不整除时候
                    if (workGroupQty.divideAndRemainder(minBatch)[1].compareTo(new BigDecimal(0)) != 0) {
                        //不整除 加1 乘以最小批量
                        BigDecimal balance = workGroupQty.divideAndRemainder(minBatch)[0];
                        BigDecimal balanceNew = balance.add(new BigDecimal(1));
                        workGroupQty = balanceNew.multiply(minBatch);
                    }
                    //单位时间产能，数据库存的是秒的产能
                    unitWorkProduct = new BigDecimal(workTimeInfo.get("unit_time_capacity").toString()).divide(new BigDecimal(3600));
                    //判断上次生产产品与这次生产产品是否一致
                    if(produceLineUseBean.getLastFMaterialId()==fMaterialId){
                        //表示工作中心生产剩余产能够用
                        if(produceLineUseBean.getBalanceCapacity().compareTo(workGroupQty)>=0){
                            //重新计算剩余产能
                            produceLineUseBean.setBalanceCapacity(
                                    produceLineUseBean.getBalanceCapacity().subtract(workGroupQty));
                            //重新计算剩余时间
                            produceLineUseBean.setBalanceWorkTime(produceLineUseBean.getBalanceWorkTime()
                                    .subtract(workGroupQty.divide(unitWorkProduct,SMALL_NUMBER,BigDecimal.ROUND_HALF_EVEN) ));
                            produceLineUseMap.put(produceLineUseKey,produceLineUseBean);
                            saveProduceLineUse(fMaterialId,workCoreId,pLineId,0,productionDate,workTimeId,
                                    classGroupId,customerId,produceLineUseBean.getAddWorkTime(),
                                    produceLineUseBean.getBalanceWorkTime(),produceLineUseBean.getBalanceCapacity(),
                                    workGroupQty,workGroupQty);
                            coreFlag = false;
                            continue;
                        }else {
                            //重新计算剩余产能
                            produceLineUseBean.setBalanceCapacity(new BigDecimal(0));
                        }
                    }else{//不是同一个产品 不能使用剩余产能，需要重新计算
                        //重新计算剩余产能
                        produceLineUseBean.setBalanceCapacity(new BigDecimal(0));
                    }
                    //第一次使用工作中心  班组
                    if(produceLineUseMap.get(produceLineUseKey)==null){
                        //计算班次工作时间
                        workTime = getWorkTime(workTimeInfo.get("begin_time").toString(),
                                workTimeInfo.get("end_time").toString());
                        produceLineUseBean.setWorkTotalTime(workTime);
                    }else{
                        //正常上班
                        if(!isAddWorkTime){
                            workTime = produceLineUseBean.getBalanceWorkTime();
                            if(workTime.compareTo(new BigDecimal(0))==0){
                                continue;
                            }
                        }else{
                            //加班
                            produceLineUseBean.setFullWork(produceLineUseBean.getWorkTotalTime()
                                    .add(minAddTime).compareTo(maxWorkTotalTime)>=0);
                            //如果加班时超过了总时间12小时 取相差时间为工作时间   如果小于12小时取最小工作时间为准
                            workTime = produceLineUseBean.isFullWork()? maxWorkTotalTime
                                    .subtract(produceLineUseBean.getWorkTotalTime()):minAddTime;
                            produceLineUseBean.setAddWorkTime(produceLineUseBean.getAddWorkTime().add(workTime));
                            produceLineUseBean.setWorkTotalTime(produceLineUseBean.getWorkTotalTime().add(workTime));
                        }
                    }
                    //判断工作组是否超时使用
                    BigDecimal workGroupTime = workGroup.get(demandDate+","+classGroupId);
                    if(workGroupTime!=null&&
                            workGroupTime.compareTo(maxWorkTotalTime)>=0){
                        continue;
                    }else{
                        workGroupTime = workGroupTime==null?new BigDecimal(0):workGroupTime;
                        if((workGroupTime.add(workTime)).compareTo(maxWorkTotalTime)>=0){
                            workTime = maxWorkTotalTime.subtract(workGroupTime);
                        }
                        workGroupTime = workGroupTime.add(workTime);
                    }
                    //当前生产的产品id
                    produceLineUseBean.setLastFMaterialId(fMaterialId);
                    //计算方法 生产中心合格率*班组单位时间产能*班次工作时间
                    workTimeCapacity = unitWorkProduct.multiply(workTime);
                    //班组产能  大于班组计划产能
                    if(workTimeCapacity.compareTo(workGroupQty)>=0){
                        //剩余产能
                        produceLineUseBean.setBalanceCapacity(workTimeCapacity.subtract(workGroupQty));
                        //剩余时间  剩余产能除单位时间产能
                        produceLineUseBean.setBalanceWorkTime(produceLineUseBean.getBalanceCapacity()
                                .divide(unitWorkProduct,SMALL_NUMBER,BigDecimal.ROUND_HALF_EVEN));
                        actualQty = workGroupQty.multiply(pRate).divide(new BigDecimal(100));
                        workCoreTotalCapacity = workCoreTotalCapacity.add(workGroupQty);
                        workGroup.put(demandDate+","+classGroupId,workGroupTime.subtract(produceLineUseBean.getBalanceWorkTime()));
                        coreFlag = false;
                    }else{
                        BigDecimal workTimeCapacityNew = (workTimeCapacity.divide(minBatch,0, RoundingMode.FLOOR)).multiply(minBatch);
                        produceLineUseBean.setBalanceCapacity(workTimeCapacityNew.subtract(workTimeCapacity));
                        produceLineUseBean.setBalanceWorkTime(produceLineUseBean.getBalanceCapacity()
                                .divide(unitWorkProduct,SMALL_NUMBER,BigDecimal.ROUND_HALF_EVEN));
                        workCoreTotalCapacity = workCoreTotalCapacity.add(workTimeCapacityNew);
                        workGroup.put(demandDate+","+classGroupId,workGroupTime);
                    }
                    produceLineUseMap.put(produceLineUseKey,produceLineUseBean);
                    saveProduceLineUse(fMaterialId,workCoreId,pLineId,produceLineUseBean.isFullWork()?1:0,productionDate,workTimeId,
                            classGroupId,customerId,produceLineUseBean.getAddWorkTime(),
                            produceLineUseBean.getBalanceWorkTime(),produceLineUseBean.getBalanceCapacity(),
                            workGroupQty,actualQty);
                    if (!coreFlag) {
                        break;
                    }
                }
                //表示产量已够或者满负荷生产了
                if (!coreFlag) {
                    break;
                } else {//如果共生产中心所有班组正常情况不能满足产能  需要加班
                    isAddWorkTime = true;
                }
            }

        }
    }
    public static void main(String[] args) {
        System.out.println(DateUtil.beforeDay("2017-03-01"));
    }
}
