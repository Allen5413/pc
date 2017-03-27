package com.allen.service.basic.productionplan.impl;

import com.allen.dao.basic.productionplan.FindProductionPlanDao;
import com.allen.entity.basic.ProductionPlan;
import com.allen.service.basic.productionplan.FindProductionPlanService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 包路径：com.allen.service.basic.productionplan.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:33
 */
@Service
public class FindProductionPlanServiceImpl implements FindProductionPlanService {
    @Resource
    private FindProductionPlanDao findProductionPlanDao;
    @Override
    public List<ProductionPlan> find(Map<String, Object> params) {
        List<ProductionPlan> productionPlans = findProductionPlanDao.find(params);
        Map<String,HashMap<String,Object>> plans = null;
        List<ProductionPlan> resultPlans = new ArrayList<ProductionPlan>();;
        ProductionPlan productionPlanNew = null;
        long fMaterialId = -1;
        HashMap<String,Object> demandDate = null;
        for (ProductionPlan productionPlan:productionPlans){
            if(fMaterialId==-1||fMaterialId!=productionPlan.getProductId()){
                productionPlanNew = new ProductionPlan();
                productionPlanNew.setProductNo(productionPlan.getProductNo());
                productionPlanNew.setProductName(productionPlan.getProductName());
                productionPlanNew.setGrossNum(productionPlan.getGrossNum());
                plans = new HashMap<String, HashMap<String, Object>>();
                productionPlanNew.setPlans(plans);
                resultPlans.add(productionPlanNew);
            }
            demandDate = new HashMap<String, Object>();
            demandDate.put("demandNum",productionPlan.getDemandNum());
            demandDate.put("productionNum",productionPlan.getProductionNum());
            demandDate.put("planNum",productionPlan.getPlanNum());
            demandDate.put("actualProductionNum",productionPlan.getActualProductionNum());
            demandDate.put("capacity",productionPlan.getCapacity());
            plans.put(DateUtil.getFormattedString(productionPlan.getProductionDate(),DateUtil.shortDatePattern),demandDate);
            productionPlanNew.setStockNum(productionPlanNew.getStockNum().add(productionPlan.getStockNum()));
            productionPlanNew.setPlanTotalNum(productionPlanNew.getPlanTotalNum().add(productionPlan.getPlanTotalNum()));
            fMaterialId = productionPlan.getProductId();
        }
        return resultPlans;
    }
}
