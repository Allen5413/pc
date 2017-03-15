package com.allen.service.basic.calculation.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecoreproduct.FindProduceLineCoreProductDao;
import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductInventory;
import com.allen.entity.basic.WorkTime;
import com.allen.entity.calculation.PlanDayMaterial;
import com.allen.service.basic.calculation.CalculationService;
import com.allen.service.basic.factorydate.FindIsWorkByDateService;
import com.allen.service.basic.producelineuse.AddProduceLineUseService;
import com.allen.service.basic.product.FindProductByPlanService;
import com.allen.service.basic.productinventory.EditProductInventoryService;
import com.allen.service.basic.productinventory.FindProInvByPIdService;
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
    @Override
    @Transactional
    public boolean calculation() throws Exception {
        //清空临时表信息

        String start = "2017-03-01";//计划开始时间
        String end = "2017-03-07";//计划结束时间
        BigDecimal minAddTime = new BigDecimal(4);//最低加班时间
        //获取生产计划的产品信息
        List<PlanOrder> planOrders = findProductByPlanService.findProductByPlan();
        //功能根据生产计划产品  获取库存信息 格式为Map<String,ProductInventory> 产品id 库存信息
        Map<String,ProductInventory> pInvMaps = getProductInv();
        //格式为{客户ID，产品：[{生产日期:产量},{生产日期:产量｝]}
        Map<String,LinkedHashMap<String,PlanDayMaterial>> produce = new LinkedHashMap<String, LinkedHashMap<String,PlanDayMaterial>>();
        String lastProductId = "-1";
        LinkedHashMap<String,PlanDayMaterial> demandDatePlanQty = null;//每日计划产量
        String produceDate = null;
        PlanDayMaterial planDayMaterial = null;
        String level = null;//产品级别
        for(PlanOrder planOrder:planOrders){
            //获取每个订单的产品信息
            List<Map> products = planOrder.getSortProducts();
            System.out.println(products);
            //生产日期
            produceDate  = DateUtil.getFormattedString(planOrder.getFDEMANDDATE(),DateUtil.shortDatePattern);
            for(Map product:products){
                if("-1".equals(lastProductId)||produce.get(planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString())==null){
                    demandDatePlanQty = new LinkedHashMap<String, PlanDayMaterial>();
                    produce.put(planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString(),demandDatePlanQty);
                }else{
                    demandDatePlanQty = produce.get(planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString());
                }
                if(demandDatePlanQty.get(produceDate)==null){
                    planDayMaterial = new PlanDayMaterial();
                    //计划产能
                    planDayMaterial.setUseQty(planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString())));
                    planDayMaterial.setCustomerId(Long.valueOf(planOrder.getFCUSTID()));
                    planDayMaterial.setDemandDate(produceDate);
                    if(product.get("childs")!=null){
                        planDayMaterial.setChilds((ArrayList)product.get("childs"));
                    }

                    demandDatePlanQty.put(produceDate,planDayMaterial);
                }else{
                    //同一产品 同一计划生产日期累加
                    planDayMaterial = demandDatePlanQty.get(produceDate);
                    planDayMaterial.setUseQty(planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString())
                            .add(planDayMaterial.getUseQty())));
                }
                lastProductId = planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString();
            }
        }
        //计算产品的产能信息
        Set<String> keys = produce.keySet();
        Set<String> materialDemandDate = null;
        BigDecimal useQty = null;//生产计划量
        Date productionDate = null;//生产日期
        BigDecimal unitWorkProduct = new BigDecimal(0);//单位时间产能
        BigDecimal actualQty = new BigDecimal(0);//实际产量
        BigDecimal workTime = new BigDecimal(0);//工作时间
        String lastPLine = null;//生产线
        String lastWorkCore = null;//工作中心
        String lastClassGroup = null;//工作组
        BigDecimal pRate = new BigDecimal(0);//产品合格率
        BigDecimal minCapacity = new BigDecimal(0);//生产线中最小产能
        Map<String,LinkedHashMap<String,LinkedHashMap<String,Map>>> workCores = null;//生产线工作中心
        LinkedHashMap<String,LinkedHashMap<String,Map>> classGroups = null;//工作组
        LinkedHashMap<String,Map> workTimes = null;//班次信息
        ProductInventory productInventory = null;//单个产品库存信息
        for(String key:keys){
            //获取产品id
            String[] ids = key.split(",");
            //产品id
            String fMaterialId = key;
            //客户id
           // long customerId = Long.valueOf(ids[0]);
            materialDemandDate = produce.get(key).keySet();
            //计算每个生产计划生产日的各个工作中心的产量
            for(String demandDate:materialDemandDate){
                productionDate = DateUtil.getFormatDate(demandDate,DateUtil.shortDatePattern);
                //产品计划产量
                useQty = produce.get(key).get(demandDate).getUseQty();
                //获取库存信息
                productInventory = pInvMaps.get(fMaterialId);
                //判断计划成产时间是否正常上班
                if(!findIsWorkByDateService.find(demandDate,start,end)){//不上班时产能为0
                     if(DateUtil.compareDate(demandDate,start)==0){//计划第一天 直接入临时库存
                         productInventory.setProductNum(productInventory.getProductNum().multiply(useQty));
                         pInvMaps.put(fMaterialId,productInventory);
                     }else{//倒推生产计划 例如：1 2 3 4，3不上班  倒推 1 2 产能都不够用的时候，3是否直接加班？还是让计划日期跑完了来在算不上班的加班

                     }
                    continue;
                }
                //库存够用
                if(productInventory.getProductNum().subtract(productInventory.getSafe()).compareTo(useQty)>0){
                    //临时库存这个不需要表存 直接数据结构存
                    productInventory.setProductNum(productInventory.getProductNum().subtract(productInventory.getSafe()).subtract(useQty));
                    pInvMaps.put(fMaterialId,productInventory);
                    continue;
                }else{
                    useQty = useQty.subtract(productInventory.getProductNum()).add(productInventory.getSafe());
                }
                //获取产品的生产线 生产中心 工作租 班次信息
                List<Map> produceLines = findProduceLineUseDao.findUnUserProduceLine(Long.valueOf(fMaterialId),productionDate,0);
                //格式化分组生产线  工作中心  工作组 班次信息
                Map<String,Map<String,LinkedHashMap<String,LinkedHashMap<String,Map>>>> pLines =
                        new LinkedHashMap<String, Map<String, LinkedHashMap<String, LinkedHashMap<String,Map>>>>();
                lastPLine = null;
                workCores = null;
                classGroups = null;
                workTimes = null;
                lastWorkCore = null;
                lastClassGroup = null;
                for (Map pLine:produceLines){
                    //生产线不同时候
                    if(lastPLine==null||!pLine.get("produce_line_id").toString().equals(lastPLine)){
                        workCores = new HashMap<String, LinkedHashMap<String, LinkedHashMap<String,Map>>>();
                        pLines.put(pLine.get("produce_line_id").toString(),workCores);
                    }
                    //工作中心不同
                    if(lastPLine==null||!pLine.get("produce_line_id").toString().equals(lastPLine)||
                            !pLine.get("work_core_id").toString().equals(lastWorkCore) ){
                        classGroups = new LinkedHashMap<String, LinkedHashMap<String,Map>>();
                        workCores.put(pLine.get("work_core_id").toString(),classGroups);
                    }
                    //工作组不同
                    if(lastPLine==null||!pLine.get("produce_line_id").toString().equals(lastPLine)||
                            !pLine.get("work_core_id").toString().equals(lastWorkCore)||
                            !pLine.get("class_group_id").toString().equals(lastClassGroup)){
                        workTimes =  new LinkedHashMap<String, Map>();
                        classGroups.put(pLine.get("class_group_id").toString(),workTimes);
                    }
                    workTimes.put(pLine.get("work_time_id").toString(),pLine);
                    lastPLine = pLine.get("produce_line_id").toString();
                    lastWorkCore = pLine.get("work_core_id").toString();
                    lastClassGroup = pLine.get("class_group_id").toString();
                }
                Set<String> pLineIds = pLines.keySet();//生产线id
                Set<String> workCoreIds = null;//生产中心id
                Set<String> classGroupIds = null;//班组信息
                Set<String> workTimeIds = null;//班次信息
                workCores = null;//生产线工作中心
                classGroups = null;//工作组
                workTimes = null;//班次信息
                boolean lineFlag = true;
                boolean coreFlag = true;
                BigDecimal lineTotalCapacity = new BigDecimal(0);//产品当天产能
                //循环生产线信息
                for(String pLineId:pLineIds){
                    workCores = pLines.get(pLineId);//生产中心
                    workCoreIds = workCores.keySet();
                    minCapacity = new BigDecimal(0);//生产线最小产能
                    //循环生产中心
                    for(String workCoreId:workCoreIds) {
                        classGroups = workCores.get(workCoreId);//班组信息
                        classGroupIds = classGroups.keySet();
                        //实际产量
                        actualQty = new BigDecimal(0);
                        coreFlag = true;
                        //循环班组
                        for(String classGroupId:classGroupIds) {
                            workTimes = classGroups.get(classGroupId);//班组对应班次信息
                            workTimeIds = workTimes.keySet();
                            //循环班次计算产能
                            for(String workTimeId:workTimeIds) {
                                //判断班组对应 班次是否使用
                               if(workTimes.get(workTimeId).get("capacity")!=null){
                                   continue;
                               }
                                //生产中心产品合格率
                                pRate = new BigDecimal(workTimes.get(workTimeId).get("qualified_rate").toString());
                                //计算班次工作时间
                                workTime = getWorkTime(workTimes.get(workTimeId).get("begin_time").toString(),
                                        workTimes.get(workTimeId).get("end_time").toString());
                                //单位时间产能，数据库存的是秒的产能
                                unitWorkProduct = new BigDecimal(workTimes.get(workTimeId).get("unit_time_capacity").toString()).divide(new BigDecimal(3600));
                                //计算方法 生产中心合格率*班组单位时间产能*班次工作时间+上个班组的产能
                                actualQty = unitWorkProduct.multiply(workTime).multiply(pRate).divide(new BigDecimal(100)).add(actualQty);
                                //记录生产线使用情况
                                ProduceLineUse produceLineUse = new ProduceLineUse();
                                produceLineUse.setCapacity(actualQty);
                                produceLineUse.setPlanQuantity(useQty);
                                produceLineUse.setIsFull(0);
                                produceLineUse.setFlag(1);
                                produceLineUse.setWorkCoreId(Long.valueOf(workCoreId));
                                //产品id
                                produceLineUse.setProductId(Long.valueOf(fMaterialId));
                                //生产线id
                                produceLineUse.setProduceLineId(Long.valueOf(pLineId));
                                //生产日期
                                produceLineUse.setProductionDate(productionDate);
                                //班次id
                                produceLineUse.setWorkTimeId(Long.valueOf(workTimeId));
                                //班组id
                                produceLineUse.setWorkTeamId(Long.valueOf(classGroupId));
                                //班次序号
                                produceLineUse.setWorkTimeSno(Long.valueOf(workTimes.get(workTimeId).get("workTimeSno").toString()));
                                //班组序号
                                produceLineUse.setWorkTeamSno(Long.valueOf(workTimes.get(workTimeId).get("sno").toString()));
                                //客户id
                                //produceLineUse.setCustomerId(customerId);
                                addProduceLineUseService.addProduceLineUse(produceLineUse);
                                if(actualQty.compareTo(useQty)>0){//实际产量大于计划产量
                                    coreFlag = false;
                                    break;
                                }
                                //原因是先班组不够用了 然后工作模式多班次
                            }
                            if(!coreFlag){
                                break;
                            }
                        }
                        if(coreFlag){//如果生产中心有产能不足够的 循环生产线
                            lineFlag = false;
                        }
                        if(minCapacity.compareTo(new BigDecimal(0))==0){
                            minCapacity = actualQty;
                        }else {
                            minCapacity = minCapacity.compareTo(actualQty)>0?actualQty:minCapacity;
                        }
                    }
                    lineTotalCapacity = lineTotalCapacity.add(minCapacity);
                    if(lineFlag){
                        break;
                    }
                }
                //更新库存信息
                productInventory.setProductNum(lineTotalCapacity.subtract(useQty));
                pInvMaps.put(fMaterialId,productInventory);
            }
        }
        //System.out.println(produce);
        return false;
    }

    /**
     * 功能:获取最小工作时间
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal getWorkTime(String startTime,String endTime){
        Date start = DateUtil.getFormatDate(startTime,DateUtil.longDatePattern);
        Date end = DateUtil.getFormatDate(endTime,DateUtil.longDatePattern);
        return new BigDecimal((float)(end.getTime() - start.getTime())/3600/1000).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 功能：当天产能不够时，查找该产品的前一天的生产计划日期
     * @param demandDate 不够产能的当天时间
     * @param start 计划安排的开始时间
     * @param end 计划安排结束时间
     * @param plans 产品的生产安排计划
     * @return
     */
    private String getBeforePlanDate(String demandDate,String start,String end,
                                     LinkedHashMap<String,PlanDayMaterial> plans) throws Exception {
        String beforeDate = DateUtil.beforeDay(demandDate);
        if(DateUtil.compareDate(beforeDate,start)<=0){//代表前一天已经是最开始时间不能再找
           return start;
        }
        if(plans.get(beforeDate)==null){//前一天没有安排工作计划
           return getBeforePlanDate(beforeDate,start,end,plans);
        }
        return beforeDate;
    }

    /**
     * 功能：获取产品库存信息
     * @return
     */
    private Map<String,ProductInventory> getProductInv(){
        Map<String,ProductInventory> pInvMaps = new HashMap<String, ProductInventory>();
        ProductInventory pInvObj = new ProductInventory();
        pInvObj.setProductNum(new BigDecimal(220));//产品库存
        pInvObj.setSafe(new BigDecimal(200));//安全库存
        pInvObj.setMax(new BigDecimal(400));//最大库存
        pInvObj.setMin(new BigDecimal(100));//最小库存
        pInvMaps.put("114937",pInvObj);
        return pInvMaps;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.beforeDay("2017-03-01"));
    }
}
