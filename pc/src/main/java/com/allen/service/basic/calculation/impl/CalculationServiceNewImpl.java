package com.allen.service.basic.calculation.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.dao.basic.producelinecoreproduct.FindProduceLineCoreProductDao;
import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.entity.basic.MaterialStock;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductionPlan;
import com.allen.entity.calculation.PlanDayMaterial;
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
import com.allen.service.basic.productionplan.ReturnProductionPlanService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 包路径：com.allen.service.basic.calculation.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-29 11:26
 */
@Service
public class CalculationServiceNewImpl implements CalculationService {
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
    @Resource
    private ReturnProductionPlanService returnProductionPlanService;
    //保留小数位数
    private static final int SMALL_NUMBER= 0;
    private static final BigDecimal ONE = new BigDecimal(1);
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    //格式为{产品ID：[{生产日期:PlanDayMaterial},{生产日期:PlanDayMaterial｝]}
    Map<Long, LinkedHashMap<String, PlanDayMaterial>> produce = new LinkedHashMap<Long, LinkedHashMap<String, PlanDayMaterial>>();
    //产品每天计划信息
    PlanDayMaterial planDayMaterial = null;
    //产品库存信息
    Map<Long, MaterialStock> pInvMaps = null;
    //生产计划时间key
    Set<String> materialDemandDate = null;
    String start = null;//计划开始时间
    String end = null;//计划结束时间
    BigDecimal maxWorkTotalTime = new BigDecimal(24);//最高连续工作时间
    MaterialStock materialStock = null;//单个产品库存信息
    ProductionPlan productionPlan = null;//产品排班信息
    BigDecimal useQty = null;//生产计划量
    Date productionDate = null;//生产日期
    List<String[]> childMaterials = null;//记录产品的下级产品信息
    BigDecimal minChildMaterial = null;//最小子产品数量
    BigDecimal childMaterialCapacity = null;//组成产品的实际产能
    //获取产品的生产线 生产中心 工作租 班次信息
    List<Map> produceLines = null;
    //生产线key
    Set<String> pLineIds = null;
    //格式化分组生产线  工作中心  工作组 班次信息
    Map<String, Map<String, LinkedHashMap<String, Map>>> pLines = null;
    Map<String, LinkedHashMap<String, Map>> workCores = null;//生产线工作中心
    LinkedHashMap<String, Map> classGroups = null;//工作组
    Map workTimeInfo = null;//班次信息
    //产品生产线使用情况
    Map<String,Boolean> demandMaterialPLineUse = null;
    //key 为生产日期，工作组id  班组使用时间
    Map<String,BigDecimal> workGroup = null;
    //记录产品 生产日期  班组id  使用时间
    Map<String,BigDecimal> demandMaterialWorkGroup = null;
    Set<String> workCoreIds = null;//生产中心id
    Set<String> classGroupIds = null;//班组信息
    String classGroupId = null;
    BigDecimal pRate = new BigDecimal(0);//产品合格率
    BigDecimal unitWorkProduct = null;//单位时间产能
    BigDecimal maxWorkCorePro = null;//生产线最大产量
    BigDecimal minBatch = null;//最小批量
    BigDecimal workTime = new BigDecimal(0);//工作时间
    BigDecimal eighthCapacity = null;
    Integer[] addTimeStep = new Integer[]{4,4,4,4};
    Map<String,Integer> classGroupTimeStep = null;
    String minProCoreId = null;
    @Override
    public boolean calculation(String start, String end) throws Exception {
        this.start = start;
        this.end = end;
        //删除计划时间已经存在的数据
        delProductionPlanService.delProductionPlan(start,end,0);
        delProduceLineUseService.delProduceLineUse(start,end,0);
        produce = new LinkedHashMap<Long, LinkedHashMap<String, PlanDayMaterial>>();
        planDayMaterial = null;
        pInvMaps = null;
        materialDemandDate = null;
        materialStock = null;
        productionPlan = null;
        useQty = null;//生产计划量
        productionDate = null;//生产日期
        childMaterials = null;
        minChildMaterial = null;
        childMaterialCapacity = null;
        workCores = null;//生产线工作中心
        classGroups = null;//工作组
        workTimeInfo = null;//班次信息
        //格式化分组生产线  工作中心  工作组 班次信息
        pLines = null;
        produceLines = null;
        pLineIds = null;
        workCoreIds = null;//生产中心id
        classGroupIds = null;//班组信息
        pRate = null;
        unitWorkProduct = null;
        maxWorkCorePro = null;
        minBatch = null;
        classGroupId = null;
        minProCoreId = null;
        demandMaterialWorkGroup = new HashMap<String, BigDecimal>();
        workGroup = new HashMap<String, BigDecimal>();
        //获取生产计划的产品信息
        List<PlanOrder> planOrders = findProductByPlanService.findProductByPlan(start,end);
        //格式为{产品：[{生产日期:PlanDayMaterial},{生产日期:PlanDayMaterial｝]},返回产品id集合
        Set<Long> productIds = formatPlan(planOrders);
        //功能根据生产计划产品  获取库存信息 格式为Map<String,ProductInventory> 产品id 库存信息
        pInvMaps = findStockByFmaterialIdsService.find(productIds.toArray(new Long[productIds.size()]));
        //key 生产日期  班组id   值加班方式
        classGroupTimeStep = new HashMap<String, Integer>();
        //计算
        calProPlan();
        return true;
    }
    /**
     * 功能：计算产品每天的产能
     * @param fMaterialId
     * @param lessDemandDate 产能不足够那天
     * @param isBack 是否需要回退查找上一步
     * @return
     * @throws Exception
     */
    private void calMaterialDate(Long fMaterialId,String lessDemandDate,boolean isBack) throws Exception {
        //循环生产计划时间
        for (String demandDate : materialDemandDate) {
            //产品某天产能不够 重新循环的时候超过不够那天退出循环，返回还差的产能值
            if(!StringUtil.isEmpty(lessDemandDate)&&DateUtil.compareDate(demandDate,lessDemandDate)>=0){
                return ;
            }
            //产品计划产
            planDayMaterial = produce.get(fMaterialId).get(demandDate);
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
                    calMaterialDate(fMaterialId,demandDate,true);
                }
                continue;
            }
            //库存够用
            if (materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(useQty) >= 0) {
                if(useQty.compareTo(BigDecimal.ZERO)==0){
                    continue;
                }
                //临时库存这个不需要表存 直接数据结构存
                materialStock.setFQTY(materialStock.getFQTY().subtract(useQty));
                pInvMaps.put(fMaterialId, materialStock);
                //记录当天产品的实际产能
                produce.get(fMaterialId).get(demandDate).setCapacity(useQty);
                produce.get(fMaterialId).get(demandDate).setBalanceCapacity(new BigDecimal(0));
                produce.get(fMaterialId).get(demandDate).setUseQtyStock(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()));
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
                productionPlan.setBomId(planDayMaterial.getBomId());
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
                        if(produce.get(childMaterial[0])!=null&&produce.get(childMaterial[0]).get(demandDate)
                                .getUseQtyStock()!=null){
                            childMaterialCapacity = produce.get(childMaterial[0]).get(demandDate)
                                    .getUseQtyStock().divide(new BigDecimal(childMaterial[1]));
                            if (minChildMaterial.intValue() == 0) {
                                minChildMaterial = childMaterialCapacity;
                            } else {
                                minChildMaterial = minChildMaterial.compareTo(childMaterialCapacity) > 0 ? childMaterialCapacity : minChildMaterial;
                            }
                        }
                    }
                    //生产产品计划量 与下级产品的最小数量对比
                    useQty = useQty.compareTo(minChildMaterial) > 0 ? useQty : minChildMaterial;
                }
            }
            workCores = null;//生产线工作中心
            classGroups = null;//工作组
            workTimeInfo = null;//班次信息
            BigDecimal lineTotalCapacity = new BigDecimal(0);//产品当天产能 实际产量
            BigDecimal totalEighthCapacity = new BigDecimal(0);//产品当天产能 实际产量
            BigDecimal actualProductionNum = new BigDecimal(0);
            //循环生产线信息
            for (String pLineId : pLineIds) {
                eighthCapacity = new BigDecimal(0);
                //key 为生产线id classGroupId
                Map<String,BigDecimal> lineSameClassGroup = new HashMap<String, BigDecimal>();
                maxWorkCorePro = calMinLinePro(demandDate,pLineId,fMaterialId,lineSameClassGroup);
                totalEighthCapacity = totalEighthCapacity.add(eighthCapacity);
                classGroups = workCores.get(minProCoreId);//最小生产中心班组信息
                classGroupIds = classGroups.keySet();
                //循环班组
                for (String classGroupId :classGroupIds) {
                    String key = fMaterialId+","+demandDate+","+classGroupId;
                    workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                    //单位时间产能，数据库存的是秒的产能
                    unitWorkProduct = new BigDecimal(workTimeInfo.get("unit_time_capacity").toString()).divide(new BigDecimal(3600));
                    //拉通计算
                    if(demandMaterialWorkGroup!=null&&demandMaterialWorkGroup.get(key)!=null){
                        maxWorkCorePro = maxWorkCorePro.add(demandMaterialWorkGroup.get(key).multiply(unitWorkProduct));
                    }
                }

                BigDecimal lineUseQty = new BigDecimal(0);
                //计划量除以合格率 实际计划量
                lineUseQty = (useQty.divide(pRate,2,BigDecimal.ROUND_UP)).multiply(ONE_HUNDRED);
                BigDecimal useWorkTime = new BigDecimal(0);
                String key = null;
                int index = 0;
                BigDecimal workCoreUse = null;
                BigDecimal maxCoreClassGroupPro = null;
                while(true){
                    workCoreUse = new BigDecimal(0);
                    maxCoreClassGroupPro = new BigDecimal(0);
                    boolean flag = false;
                    for (String classGroupId :classGroupIds) {
                        key = fMaterialId+","+demandDate+","+classGroupId;
                        if(workGroup.get(demandDate+","+classGroupId)!=null){
                            useWorkTime = workGroup.get(demandDate+","+classGroupId);
                        }
                        if(demandMaterialWorkGroup.get(key)!=null){
                            useWorkTime = useWorkTime.subtract(demandMaterialWorkGroup.get(key));
                        }
                        workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                        unitWorkProduct = lineSameClassGroup.get(pLineId+","+classGroupId);
                        BigDecimal currentWorkTime = getWorkTime(workTimeInfo.get("begin_time").toString(),
                                workTimeInfo.get("end_time").toString());
                        minBatch = new BigDecimal(workTimeInfo.get("min_batch").toString());
                        BigDecimal coreClassPro = new BigDecimal(0);
                        if(index==0){
                            if (currentWorkTime.compareTo(useWorkTime)<=0){
                                continue;
                            }else{
                                coreClassPro = (currentWorkTime.subtract(useWorkTime)).divide(unitWorkProduct,8,BigDecimal.ROUND_HALF_EVEN);
                                maxCoreClassGroupPro = lineUseQty.subtract(workCoreUse);
                                //计划产能除以最小批量  不整除时候
                                if (maxCoreClassGroupPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                    //不整除 加1 乘以最小批量
                                    maxCoreClassGroupPro = ((maxCoreClassGroupPro.divideAndRemainder(minBatch)[0]).add(ONE)).multiply(minBatch);
                                }
                                if(coreClassPro.compareTo(maxCoreClassGroupPro)>=0){
                                    coreClassPro = maxCoreClassGroupPro;
                                }else{
                                    if (coreClassPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                        //不整除
                                        coreClassPro = (coreClassPro.divideAndRemainder(minBatch)[0]).multiply(minBatch);
                                    }
                                }
                                workCoreUse = workCoreUse.add(coreClassPro);
                            }
                        }else{
                            for(int i=0;i<index;i++){
                                currentWorkTime = currentWorkTime.add(new BigDecimal(addTimeStep[i]));
                            }
                            if (currentWorkTime.compareTo(useWorkTime)<=0){
                                continue;
                            }else{
                                coreClassPro = (currentWorkTime.subtract(useWorkTime)).divide(unitWorkProduct,8,BigDecimal.ROUND_HALF_EVEN);
                                maxCoreClassGroupPro = lineUseQty.subtract(workCoreUse);
                                //计划产能除以最小批量  不整除时候
                                if (maxCoreClassGroupPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                    //不整除 加1 乘以最小批量
                                    maxCoreClassGroupPro =  ((maxCoreClassGroupPro.divideAndRemainder(minBatch)[0]).add(ONE)).multiply(minBatch);
                                }
                                if(coreClassPro.compareTo(maxCoreClassGroupPro)>=0){
                                    coreClassPro = maxCoreClassGroupPro;
                                }else{
                                    if (coreClassPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                        //不整除 加1 乘以最小批量
                                        coreClassPro = (coreClassPro.divideAndRemainder(minBatch)[0]).multiply(minBatch);
                                    }
                                }
                                workCoreUse = workCoreUse.add(coreClassPro);
                            }
                        }
                        if(workCoreUse.compareTo(lineUseQty)>=0){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        break;
                    }
                    index++;
                    if(index==(addTimeStep.length+1)){
                        break;
                    }
                }
                actualProductionNum = actualProductionNum.add(workCoreUse);
                lineTotalCapacity = lineTotalCapacity.add(workCoreUse.multiply(pRate).divide(ONE_HUNDRED));
                //计算 排班信息
                calScheduling(workCoreUse,pLines.get(pLineId),demandDate,pLineId,fMaterialId,lineSameClassGroup);
                if(lineUseQty.compareTo(workCoreUse)<=0) {
                    break;
                }
            }
            if(lineTotalCapacity.compareTo(useQty)<=0){//生产线的总数量小于计划量
                planDayMaterial.setFullWork(true);
            }
            //记录当天产品的实际产能
            produce.get(fMaterialId).get(demandDate).setCapacity(lineTotalCapacity);
            //如果库存有多余的 当天实际有的产品数量要加上库存
            if(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(BigDecimal.ZERO)>=0){
                produce.get(fMaterialId).get(demandDate).setUseQtyStock(lineTotalCapacity.add(
                        materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK())));
            }else{
                //库存没有多余的  当天实际产品数量以生产量为准
                produce.get(fMaterialId).get(demandDate).setUseQtyStock(lineTotalCapacity);
            }
            planDayMaterial.setStorageNum(lineTotalCapacity.subtract(useQty));
            materialStock.setFQTY(lineTotalCapacity.subtract(useQty));
            pInvMaps.put(fMaterialId, materialStock);
            //所有生产线满负荷生产  不能满足生产计划
            if(actualProductionNum.compareTo(BigDecimal.ZERO)>0){
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
                productionPlan.setBomId(planDayMaterial.getBomId());
                productionPlan.setCapacity(totalEighthCapacity);
                addProductionPlanService.addProductionPlan(productionPlan);
            }
            if (lineTotalCapacity.compareTo(useQty) < 0) {
                if(isBack) {
                    calMaterialDate(fMaterialId, demandDate, true);
                }
            }
        }
    }
    /**
     * 计算排班信息
     * @param proNum 生产量
     * @param workCores 工作中心信息
     */
    public void calScheduling(BigDecimal proNum,Map<String, LinkedHashMap<String, Map>> workCores,
                              String demandDate,String pLineId,Long fMaterialId,Map<String,BigDecimal> lineSameClassGroup){
        workCoreIds = workCores.keySet();
        //循环生产中心
        for (String workCoreId : workCoreIds) {
            classGroups = workCores.get(workCoreId);//班组信息
            classGroupIds = classGroups.keySet();
            BigDecimal useWorkTime = new BigDecimal(0);
            String key = null;
            int index = 0;
            BigDecimal workCoreUse = null;
            BigDecimal maxCoreClassGroupPro = null;
            while(true){
                workCoreUse = new BigDecimal(0);
                boolean flag = false;
                for (String classGroupId :classGroupIds) {
                    key = fMaterialId+","+demandDate+","+classGroupId;
                    if(workGroup.get(demandDate+","+classGroupId)!=null){
                        useWorkTime = workGroup.get(demandDate+","+classGroupId);
                    }
                    if(demandMaterialWorkGroup.get(key)!=null){
                        useWorkTime = useWorkTime.subtract(demandMaterialWorkGroup.get(key));
                    }
                    workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                    unitWorkProduct = lineSameClassGroup.get(pLineId+","+classGroupId);
                    BigDecimal currentWorkTime = getWorkTime(workTimeInfo.get("begin_time").toString(),
                            workTimeInfo.get("end_time").toString());
                    minBatch = new BigDecimal(workTimeInfo.get("min_batch").toString());
                    BigDecimal coreClassPro = new BigDecimal(0);
                    if(index==0){
                        if (currentWorkTime.compareTo(useWorkTime)<=0){
                            continue;
                        }else{
                            coreClassPro = (currentWorkTime.subtract(useWorkTime)).divide(unitWorkProduct,8,BigDecimal.ROUND_HALF_EVEN);
                            maxCoreClassGroupPro = proNum.subtract(workCoreUse);
                            //计划产能除以最小批量  不整除时候
                            if (maxCoreClassGroupPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                //不整除 加1 乘以最小批量
                                maxCoreClassGroupPro =  ((maxCoreClassGroupPro.divideAndRemainder(minBatch)[0]).add(ONE)).multiply(minBatch);
                            }
                            if(coreClassPro.compareTo(maxCoreClassGroupPro)>=0){
                                coreClassPro = maxCoreClassGroupPro;
                            }else{
                                if (coreClassPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                    //不整除 加1 乘以最小批量
                                    coreClassPro = (coreClassPro.divideAndRemainder(minBatch)[0]).multiply(minBatch);
                                }
                            }
                            workCoreUse = workCoreUse.add(coreClassPro);
                        }
                    }else{
                        for(int i=0;i<index;i++){
                            currentWorkTime = currentWorkTime.add(new BigDecimal(addTimeStep[i]));
                        }
                        if (currentWorkTime.compareTo(useWorkTime)<=0){
                            continue;
                        }else{
                            coreClassPro = (currentWorkTime.subtract(useWorkTime)).divide(unitWorkProduct,8,BigDecimal.ROUND_HALF_EVEN);
                            maxCoreClassGroupPro = proNum.subtract(workCoreUse);
                            //计划产能除以最小批量  不整除时候
                            if (maxCoreClassGroupPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                //不整除 加1 乘以最小批量
                                maxCoreClassGroupPro =  ((maxCoreClassGroupPro.divideAndRemainder(minBatch)[0]).add(ONE)).multiply(minBatch);
                            }
                            if(coreClassPro.compareTo(maxCoreClassGroupPro)>=0){
                                coreClassPro = maxCoreClassGroupPro;
                            }else{
                                if (coreClassPro.divideAndRemainder(minBatch)[1].compareTo(BigDecimal.ZERO) != 0) {
                                    //不整除 加1 乘以最小批量
                                    coreClassPro = (coreClassPro.divideAndRemainder(minBatch)[0]).multiply(minBatch);
                                }
                            }
                            workCoreUse = workCoreUse.add(coreClassPro);
                        }
                    }
                    //实际工作时间
                    workTime = coreClassPro.multiply(unitWorkProduct);
                    key = fMaterialId+","+demandDate+","+classGroupId;
                    //产品当天使用班组时间情况
                    if(demandMaterialWorkGroup!=null&&demandMaterialWorkGroup.get(key)!=null){
                        workTime = workTime.subtract(demandMaterialWorkGroup.get(key));
                        demandMaterialWorkGroup.put(key,workTime.add(demandMaterialWorkGroup.get(key)));
                    }else{
                        demandMaterialWorkGroup.put(key,workTime);
                    }
                    //判断当天的班组是否使用
                    if(workGroup!=null&&workGroup.get(demandDate+","+classGroupId)!=null){
                        workGroup.put(demandDate+","+classGroupId,workTime.add(workGroup.get(demandDate+","+classGroupId)));
                    }else {
                        workGroup.put(demandDate+","+classGroupId,workTime);
                    }
                    BigDecimal addTime = (workGroup.get(demandDate+","+classGroupId)).subtract(getWorkTime(workTimeInfo.get("begin_time").toString(),
                            workTimeInfo.get("end_time").toString()));
                    BigDecimal balanceTime = maxWorkTotalTime.subtract(workGroup.get(demandDate+","+classGroupId));
                    saveProduceLineUse(fMaterialId,workCoreId,pLineId,0,productionDate,
                            workTimeInfo.get("work_time_id").toString(),classGroupId,new Long(1),
                            addTime.compareTo(BigDecimal.ZERO)>0?addTime:BigDecimal.ZERO,
                            balanceTime,balanceTime.multiply(unitWorkProduct),
                            proNum,coreClassPro,workTime,workGroup.put(demandDate+","+classGroupId,workTime).subtract(workTime));
                    if(workCoreUse.compareTo(proNum)>=0){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    break;
                }
                index++;
                if(index==(addTimeStep.length+1)){
                    break;
                }
            }
        }
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
                                    BigDecimal capacity,BigDecimal workTime,BigDecimal workStart){
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
        produceLineUse.setWorkTime(workTime);
        produceLineUse.setWorkStart(workStart);
        addProduceLineUseService.addProduceLineUse(produceLineUse);
    }
    BigDecimal lineMaxPro = null;
    BigDecimal workCoreBalanceTotalPro = null;
    /**
     * 功能:计算生产线最大产能
     * @param pLineId
     * @param fMaterialId
     * @return
     */
    private BigDecimal calMinLinePro(String demandDate,String pLineId,long fMaterialId,Map<String,BigDecimal> lineSameClassGroup ){
        workCores = pLines.get(pLineId);//生产中心
        workCoreIds = workCores.keySet();
        lineMaxPro = new BigDecimal(0);
        maxWorkCorePro = new BigDecimal(Integer.MAX_VALUE);
        //循环生产中心
        for (String workCoreId : workCoreIds) {
            classGroups = workCores.get(workCoreId);//班组信息
            classGroupIds = classGroups.keySet();
            //循环班组
            for (String classGroupId :classGroupIds) {
                workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                //单位时间产能，数据库存的是秒的产能
                unitWorkProduct = new BigDecimal(workTimeInfo.get("unit_time_capacity").toString()).divide(new BigDecimal(3600));
                if(lineSameClassGroup.get(pLineId+","+classGroupId)==null){
                    lineSameClassGroup.put(pLineId+","+classGroupId,ONE.divide(unitWorkProduct,8,BigDecimal.ROUND_HALF_EVEN));
                }else{
                    lineSameClassGroup.put(pLineId+","+classGroupId,(ONE.divide(unitWorkProduct,8,
                            BigDecimal.ROUND_HALF_EVEN)).add(lineSameClassGroup.get(pLineId+","+classGroupId)));
                }
            }
        }
        BigDecimal minWorkCoreTotalPro = new BigDecimal(Integer.MAX_VALUE);
        String key = null;
        BigDecimal useWorkTime = null;
        //循环生产中心
        for (String workCoreId : workCoreIds) {
            classGroups = workCores.get(workCoreId);//班组信息
            classGroupIds = classGroups.keySet();
            workCoreBalanceTotalPro = new BigDecimal(0);
            BigDecimal coreTotalPro = new BigDecimal(0);
            BigDecimal workCoreTotalPro = new BigDecimal(0);
            useWorkTime = new BigDecimal(0);
            //循环班组
            for (String classGroupId :classGroupIds) {
                workTimeInfo = classGroups.get(classGroupId);//班组对应班次信息
                //每个产品需要的时间
                unitWorkProduct = lineSameClassGroup.get(pLineId+","+classGroupId);
                key = fMaterialId+","+demandDate+","+classGroupId;
                if(workGroup.get(demandDate+","+classGroupId)!=null){
                    useWorkTime = workGroup.get(demandDate+","+classGroupId);
                }
                if(demandMaterialWorkGroup.get(key)!=null){
                    useWorkTime = useWorkTime.subtract(demandMaterialWorkGroup.get(key));
                }
                coreTotalPro = coreTotalPro.add(ONE.divide(unitWorkProduct,2,BigDecimal.ROUND_HALF_EVEN));
                workCoreTotalPro = workCoreTotalPro.add((maxWorkTotalTime.subtract(useWorkTime)).divide(unitWorkProduct,2,BigDecimal.ROUND_HALF_EVEN));
                workCoreBalanceTotalPro = workCoreBalanceTotalPro.add(maxWorkTotalTime.divide(unitWorkProduct,2,BigDecimal.ROUND_HALF_EVEN));                ;
                //判断当天的班组是否使用
                if(workGroup!=null&&workGroup.get(demandDate+","+classGroupId)!=null){
                    workCoreBalanceTotalPro = workCoreBalanceTotalPro.subtract(
                            workGroup.get(demandDate+","+classGroupId).divide(unitWorkProduct,2,BigDecimal.ROUND_HALF_EVEN));
                }
            }
            workCoreTotalPro = workCoreTotalPro.multiply(new BigDecimal(workTimeInfo.get("qualified_rate").toString()));
            maxWorkCorePro = maxWorkCorePro.compareTo(workCoreBalanceTotalPro)>=0?workCoreBalanceTotalPro:maxWorkCorePro;
            if (workCoreTotalPro.compareTo(minWorkCoreTotalPro)<=0) {
                pRate = new BigDecimal(workTimeInfo.get("qualified_rate").toString());
                eighthCapacity = (new BigDecimal(8)).multiply(coreTotalPro);
                minProCoreId = workCoreId;
            }
        }
        return maxWorkCorePro;
    }
    //重新初始化数据
    @Transactional
    public void calProPlan() throws Exception {
        //获取生产计划的keys
        Set<Long> keys = produce.keySet();
        //生产计划的keys循环产品信息 fMaterialId
        for (Long fMaterialId : keys) {
            //产品的每天生产计划信息
            materialDemandDate = produce.get(fMaterialId).keySet();
            //初始化产品计划信息
            saveProPlan(fMaterialId);
            //获取产品的生产线 生产中心 工作租 班次信息
            produceLines = findProduceLineUseDao.findUnUserProduceLine(fMaterialId);
            //格式化分组生产线  工作中心  工作组 班次信息
            pLines = formatProductLine(produceLines);
            pLineIds = pLines.keySet();//生产线id
            //计算排产信息
            calMaterialDate(fMaterialId,null,true);
            //如果产品实际生产还不够  根据产品库存判断
            if(materialStock.getFQTY().subtract(materialStock.getFSAFESTOCK()).compareTo(new BigDecimal(0))<0){
                Set<String> planCycle = new LinkedHashSet<String>();
                String endNow = start;
                while (true){
                    planCycle.add(endNow);
                    endNow = DateUtil.afterDay(endNow);
                    if(DateUtil.compareDate(end,endNow)<0){
                        break;
                    }
                }
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
                    LinkedHashMap<String, PlanDayMaterial> playDayMaterials = produce.get(fMaterialId);
                    PlanDayMaterial planDayMaterialNew = null;
                    for(String demandDate:unPlanDate){
                        planDayMaterialNew = new PlanDayMaterial();
                        planDayMaterialNew.setMaterialId(fMaterialId);
                        planDayMaterialNew.setCustomerId(0);
                        planDayMaterialNew.setDemandDate(demandDate);
                        planDayMaterialNew.setUseQty(new BigDecimal(0));
                        planDayMaterialNew.setProductNo(planDayMaterial.getProductNo());
                        planDayMaterialNew.setProductName(planDayMaterial.getProductName());
                        planDayMaterialNew.setProductType(planDayMaterial.getProductType());
                        planDayMaterialNew.setBomId(planDayMaterial.getBomId());
                        playDayMaterials.put(demandDate,planDayMaterialNew);
                    }
                    produce.put(fMaterialId,playDayMaterials);
                    calMaterialDate(fMaterialId,null,false);
                }
            }
        }
        //反写生产计划
        //returnProductionPlanService.returnPlan(DateUtil.getFormatDate(start,DateUtil.shortDatePattern),
          //     DateUtil.getFormatDate(end,DateUtil.shortDatePattern));
    }

    /**
     * 功能：格式化生产计划
     * @param planOrders
     * @return
     */
    private Set<Long> formatPlan(List<PlanOrder> planOrders){
        long lastProductId = -1;
        LinkedHashMap<String, PlanDayMaterial> demandDatePlanQty = null;//每日计划产量
        String produceDate = null;
        Set<Long> productIds = new HashSet<Long>();
        for (PlanOrder planOrder:planOrders) {
            //生产日期
            produceDate = planOrder.getDemandDate();
            productIds.add(planOrder.getFMATERIALID());
            if (lastProductId==-1 || produce.get(planOrder.getFMATERIALID()) == null) {
                demandDatePlanQty = new LinkedHashMap<String, PlanDayMaterial>();
                produce.put(planOrder.getFMATERIALID(), demandDatePlanQty);
            } else {
                demandDatePlanQty = produce.get(planOrder.getFMATERIALID());
            }
            if (demandDatePlanQty.get(produceDate) == null) {
                planDayMaterial = new PlanDayMaterial();
                //计划产能
                planDayMaterial.setUseQty(planOrder.getFFIRMQTY());
                planDayMaterial.setCustomerId(Long.valueOf(planOrder.getFCUSTID()));
                planDayMaterial.setDemandDate(produceDate);
                planDayMaterial.setProductName(planOrder.getFNAME());
                planDayMaterial.setProductNo(planOrder.getFNUMBER());
                planDayMaterial.setProductType(planOrder.getFCATEGORYID());
                planDayMaterial.setChilds(planOrder.getChilds());
                planDayMaterial.setBomId(planOrder.getFBOMID());
                demandDatePlanQty.put(produceDate, planDayMaterial);
            } else {
                //同一产品 同一计划生产日期累加
                planDayMaterial = demandDatePlanQty.get(produceDate);
                planDayMaterial.setUseQty(planOrder.getFFIRMQTY().add(planDayMaterial.getUseQty()));
            }
            lastProductId = planOrder.getFMATERIALID();
        }
        return  productIds;
    }

    //初始化生产计划产能信息
    public void saveProPlan(Long fMaterialId){
        int first = 0;
        //循环生产计划时间
        if(pInvMaps==null){
            pInvMaps = new HashMap<Long, MaterialStock>();
        }
        for (String demandDate:materialDemandDate) {
            planDayMaterial = produce.get(fMaterialId).get(demandDate);
            //获取库存信息
            materialStock = pInvMaps.get(fMaterialId);
            if(materialStock==null){
                materialStock = new MaterialStock();
                materialStock.setFQTY(new BigDecimal(0));
                materialStock.setFSAFESTOCK(new BigDecimal(0));
                pInvMaps.put(fMaterialId,materialStock);
            }
            productionPlan = new ProductionPlan();
            productionPlan.setProductId(fMaterialId);
            productionPlan.setProductionDate(DateUtil.getFormatDate(demandDate,DateUtil.shortDatePattern));
            productionPlan.setDemandNum(new BigDecimal(0));
            productionPlan.setProductionNum(new BigDecimal(0));
            productionPlan.setPlanNum(planDayMaterial.getUseQty());
            productionPlan.setGrossNum(new BigDecimal(0));
            productionPlan.setPlanTotalNum(planDayMaterial.getUseQty());
            productionPlan.setBomId(planDayMaterial.getBomId());
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
    //格式化生产线信息
    private Map<String, Map<String, LinkedHashMap<String, Map>>> formatProductLine(List<Map> produceLines){
        String lastPLine = null;//生产线
        String lastWorkCore = null;//工作中心
        String lastClassGroup = null;//工作组
        Map<String, Map<String, LinkedHashMap<String, Map>>> pLines =
                new LinkedHashMap<String, Map<String, LinkedHashMap<String, Map>>>();
        for (Map pLine : produceLines) {
            //生产线不同时候
            if (lastPLine == null || !(pLine.get("produce_line_id").toString()).equals(lastPLine)) {
                workCores = new HashMap<String, LinkedHashMap<String, Map>>();
                pLines.put(pLine.get("produce_line_id").toString(), workCores);
            }
            //工作中心不同
            if (lastPLine == null || !(pLine.get("produce_line_id").toString()).equals(lastPLine) ||
                    !pLine.get("work_core_id").toString().equals(lastWorkCore)) {
                classGroups = new LinkedHashMap<String, Map>();
                workCores.put(pLine.get("work_core_id").toString(), classGroups);
            }
            //工作组不同
            if (lastPLine == null || !(pLine.get("produce_line_id").toString()).equals(lastPLine) ||
                    !pLine.get("work_core_id").toString().equals(lastWorkCore) ||
                    !pLine.get("class_group_id").toString().equals(lastClassGroup)) {
                workTimeInfo = new LinkedHashMap<String, Map>();
                classGroups.put(pLine.get("class_group_id").toString(), workTimeInfo);
            }
            classGroups.put(pLine.get("class_group_id").toString(), pLine);
            lastPLine = pLine.get("produce_line_id").toString();
            lastWorkCore = pLine.get("work_core_id").toString();
            lastClassGroup = pLine.get("class_group_id").toString();
        }
        return pLines;
    }
    /**
     * 功能:获取最小工作时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal getWorkTime(String startTime, String endTime) {
        /*Date start = DateUtil.getFormatDate(startTime, DateUtil.longDatePattern);
        Date end = DateUtil.getFormatDate(endTime, DateUtil.longDatePattern);
        return new BigDecimal((float) (end.getTime() - start.getTime()) / 3600 / 1000).setScale(2, BigDecimal.ROUND_HALF_UP);*/
        return new BigDecimal(8);
    }

}
