package com.allen.service.basic.calculation.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecoreproduct.FindProduceLineCoreProductDao;
import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductInventory;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.calculation.CalculationService;
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
    @Override
    @Transactional
    public boolean calculation() throws BusinessException {
        //清空临时表信息

        //获取生产计划的产品信息
        List<PlanOrder> planOrders = findProductByPlanService.findProductByPlan();
        //保存每个产品，每天的计划产量   实际产量  班次 模式等信息
        Map<String,LinkedHashMap<String,BigDecimal>> produce = new LinkedHashMap<String, LinkedHashMap<String,BigDecimal>>();
        //格式为{客户ID，产品：[{生产日期:产量},{生产日期:产量｝]}
        String lastProductId = "-1";
        LinkedHashMap<String,BigDecimal> demandDatePlanQty = null;//每日计划产量
        String produceDate = null;
        for(PlanOrder planOrder:planOrders){
            List<Map> products = planOrder.getProducts();
            //生产日期
            produceDate  = DateUtil.getFormattedString(planOrder.getFDEMANDDATE(),DateUtil.shortDatePattern);
            for(Map product:products){
                if("-1".equals(lastProductId)||produce.get(planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString())==null){
                    demandDatePlanQty = new LinkedHashMap<String, BigDecimal>();
                    produce.put(planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString(),demandDatePlanQty);
                }else{
                    demandDatePlanQty = produce.get(planOrder.getFCUSTID()+","+product.get("FMATERIALID").toString());
                }
                if(demandDatePlanQty.get(produceDate)==null){
                    demandDatePlanQty.put(produceDate,planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString())));
                }else{
                    demandDatePlanQty.put(produceDate,planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString())
                            .add(demandDatePlanQty.get(produceDate))));
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
        ProductInventory productInventory = null;
        for(String key:keys){
            //获取产品id
            String[] ids = key.split(",");
            //产品id
            long fMaterialId = Long.valueOf(ids[1]);
            //客户id
            long customerId = Long.valueOf(ids[0]);
            materialDemandDate = produce.get(key).keySet();
            //计算每个生产计划生产日的各个工作中心的产量
            for(String demandDate:materialDemandDate){
                productionDate = DateUtil.getFormatDate(demandDate,DateUtil.shortDatePattern);
                //产品计划产量
                useQty = produce.get(key).get(demandDate);
                //获取库存信息
                productInventory = findProInvByPIdService.findProductInventoryByProductId(fMaterialId);
                //库存够用
                if(productInventory.getProductNum().subtract(productInventory.getSafe()).compareTo(useQty)>0){
                    editProductInventoryService.editProductInventory(fMaterialId,productInventory.getProductNum().subtract(productInventory.getSafe()).subtract(useQty));
                    continue;
                }else{
                    useQty = useQty.subtract(productInventory.getProductNum()).add(productInventory.getSafe());
                }
                //获取产品的生产线 生产中心 工作租 班次信息
                List<Map> produceLines = findProduceLineUseDao.findUnUserProduceLine(fMaterialId,productionDate,0);
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
                                produceLineUse.setProductId(fMaterialId);
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
                                produceLineUse.setCustomerId(customerId);
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
                editProductInventoryService.editProductInventory(fMaterialId,lineTotalCapacity.subtract(useQty));
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

    public static void main(String[] args) {

    }
}
