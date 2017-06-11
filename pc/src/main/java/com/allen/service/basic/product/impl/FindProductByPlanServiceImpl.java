package com.allen.service.basic.product.impl;

import com.allen.dao.basic.planorder.FindPlanOrderDao;
import com.allen.dao.basic.planorder.PlanOrderDao;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.dao.basic.productselfuse.FindProductSelfUseDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.service.basic.product.FindProductByPlanService;
import com.allen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private PlanOrderDao planOrderDao;
    @Transactional
    @Override
    public List<PlanOrder> findProductByPlan(String start,String end) {
        //获取生产计划信息
        List<PlanOrder> planOrders = findPlanOrderDao.findPlanOrder(
                DateUtil.getFormatDate(start,DateUtil.shortDatePattern),
                DateUtil.getFormatDate(end,DateUtil.shortDatePattern));

        //获取订单产品信息
        Map<String,PlanOrder> productMap = new LinkedHashMap<String, PlanOrder>();
        Map<Long,Integer> materialLevel = new HashMap<Long, Integer>();
        Map<Long,Boolean> isSelectLevel = new HashMap<Long, Boolean>();
        for(PlanOrder planOrder:planOrders){
            if(planOrder.getFDEMANDQTY().compareTo(BigDecimal.ZERO)==0){
                PlanOrder oldPlanOrder = planOrderDao.findOne(planOrder.getFID());
                oldPlanOrder.setFDEMANDQTY(planOrder.getFFIRMQTY());
                planOrderDao.save(oldPlanOrder);
            }
            if(isSelectLevel.get(planOrder.getFMATERIALID())==null||isSelectLevel.get(planOrder.getFMATERIALID())==false){
                if(materialLevel.get(planOrder.getFMATERIALID())==null){
                    materialLevel.put(planOrder.getFMATERIALID(),1);
                }
                //获取产品组成
                findProductSelfUseDao.findProductChildLevel(planOrder.getFMATERIALID(),1,materialLevel);
                isSelectLevel.put(planOrder.getFMATERIALID(),true);
            }

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
            productMap.get(key).setLevel(materialLevel.get(productMap.get(key).getFMATERIALID()));
            result.add(productMap.get(key));
        }
        Collections.sort(result, new Comparator<PlanOrder>() {
            @Override
            public int compare(PlanOrder obj1, PlanOrder obj2) {
                int level1 = obj1.getLevel();
                int level2 = obj2.getLevel();
                if(level1==level2){
                    return 0;
                }
                return level1<level2?1:-1;
            }
        });
        return result;
    }
}
