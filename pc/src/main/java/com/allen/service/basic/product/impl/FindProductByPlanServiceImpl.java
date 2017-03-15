package com.allen.service.basic.product.impl;

import com.allen.dao.basic.planorder.FindPlanOrderDao;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.dao.basic.productselfuse.FindProductSelfUseDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.service.basic.product.FindProductByPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<PlanOrder> findProductByPlan() {
        //获取生产计划信息
        List<PlanOrder> planOrders = findPlanOrderDao.findPlanOrder();
        //获取订单产品信息
        Map<String,List<Map>> productMap = new HashMap<String, List<Map>>();
        for(PlanOrder planOrder:planOrders){
            Map<String,Object> product = new HashMap<String, Object>();
            product.put("FMATERIALID",planOrder.getFMATERIALID());//产品id
            product.put("useQty",1);//产品数量
            product.put("level",1);//当前产品的层级

            if(productMap.get(planOrder.getFMATERIALID()+"")==null){//同一个产品不用重新取产品组成
                planOrder.getProducts().add(product);
                //获取产品组成
                findProductSelfUseDao.findProductChild(planOrder.getProducts(),planOrder.getFMATERIALID(),new BigDecimal(1),1);
                productMap.put(planOrder.getFMATERIALID()+"",planOrder.getProducts());
            }else{
               List<Map> productMaps = productMap.get(planOrder.getFMATERIALID()+"");
               planOrder.getProducts().addAll(productMaps);
            }
            System.out.println(planOrder.getProducts());
        }
        return planOrders;
    }
}
