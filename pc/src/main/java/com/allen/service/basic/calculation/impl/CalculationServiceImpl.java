package com.allen.service.basic.calculation.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.service.basic.calculation.CalculationService;
import com.allen.service.basic.product.FindProductByPlanService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean calculation() throws BusinessException {
        //保存每个产品，每天的计划产量   实际产量  班次 模式等信息
        Map<String,LinkedHashMap<String,Map<String,Object>>> produce = new HashMap<String, LinkedHashMap<String,Map<String,Object>>>();
        //获取生产计划的产品信息
        List<PlanOrder> planOrders = findProductByPlanService.findProductByPlan();
        for(PlanOrder planOrder:planOrders){
            List<Map> products = planOrder.getProducts();
            for(Map product:products){
                //获取可用的非公共线
               List<Map> produceLines = findProduceLineUseDao.findUnUserProduceLine(
                       Long.valueOf(product.get("FMATERIALID").toString()), planOrder.getFDEMANDDATE(),0);
               if(produceLines==null || produceLines.size()==0){//找到产品可用的生产线,去查找是否有可用的公共线
                    produceLines = findProduceLineUseDao.findUnUserProduceLine(
                           Long.valueOf(product.get("FMATERIALID").toString()), planOrder.getFDEMANDDATE(),1);
               }
                //找到产品信息
               LinkedHashMap<String,Map<String,Object>> map = produce.get(planOrder.getFMATERIALID()+","+product.get("FMATERIALID").toString());
               if(map==null){//没找到初始化信息
                   map = new LinkedHashMap<String,Map<String,Object>>();
                   produce.put(planOrder.getFMATERIALID()+","+product.get("FMATERIALID").toString(),map);
               }
               String produceDate = DateUtil.getFormattedString(planOrder.getFDEMANDDATE(),DateUtil.shortDatePattern);
               //找到产品对应的某天信息 生产信息
               Map<String,Object> produceProduct = map.get(produceDate);
               if(produceProduct == null){
                   produceProduct = new HashMap<String, Object>();
                   map.put(produceDate,produceProduct);
               }
                //产品计划产量
               BigDecimal useQty = planOrder.getFFIRMQTY().multiply(new BigDecimal(product.get("useQty").toString()));
               if(produceLines==null||produceLines.size()==0){//没有生产线可以使用   产能为0
                   produceProduct.put("capacity",0);
                   produceProduct.put("planCapacity",useQty);
               }else{
                   //取得生产线信息
                   Map productLineInfo = produceLines.get(0);
                   //根据产品所在生产线需要的生产中心

                   produceProduct.put("capacity",500);
                   produceProduct.put("planCapacity",useQty);
               }
            }
        }
        System.out.println(produce);
        return false;
    }


}
