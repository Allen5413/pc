package com.allen.service.basic.product.impl;

import com.allen.dao.basic.planorder.FindPlanOrderDao;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.dao.basic.productselfuse.FindProductSelfUseDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.service.basic.product.FindProductByPlanService;
import com.allen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 包路径：com.allen.service.basic.product.impl
 * 功能说明：获取生产计划订单产品信息
 * 创建人： ly
 * 创建时间: 2017-03-04 23:11
 */
@Service
public class FindProductByPlanServiceImpl implements FindProductByPlanService {

    @Autowired
    private FindPlanOrderDao findPlanOrderDao;
    @Autowired
    private FindProductSelfUseDao findProductSelfUseDao;
    @Override
    public List<PlanOrder> findProductByPlan(String start,String end) {
        //获取生产计划信息
        List<PlanOrder> planOrders = findPlanOrderDao.findPlanOrder(
                DateUtil.getFormatDate(start,DateUtil.shortDatePattern),
                DateUtil.getFormatDate(end,DateUtil.shortDatePattern));
        //获取订单产品信息
        Map<String,PlanOrder> productMap = new LinkedHashMap<String, PlanOrder>();
        for(PlanOrder planOrder:planOrders){
            if(productMap.get(planOrder.getFMATERIALID()+","+planOrder.getDemandDate())==null){
                //查询产品第一级
                findProductSelfUseDao.findProductChild(planOrder);
                productMap.put(planOrder.getFMATERIALID()+","+planOrder.getDemandDate(),planOrder);
            }else{
                PlanOrder planOrderOld = productMap.get(planOrder.getFMATERIALID()+","+planOrder.getDemandDate());
                planOrderOld.setFFIRMQTY(planOrderOld.getFFIRMQTY().add(planOrder.getFFIRMQTY()));
                productMap.put(planOrder.getFMATERIALID()+","+planOrder.getDemandDate(),planOrderOld);
            }
        }
        Set<String> keys = productMap.keySet();
        List<PlanOrder> result = new ArrayList<PlanOrder>();
        for (String key:keys){
            result.add(productMap.get(key));
        }
        return result;
    }
}
