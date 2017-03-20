package com.allen.service.basic.productionplan.impl;

import com.allen.dao.basic.productionplan.ProductionPlanDao;
import com.allen.entity.basic.ProductionPlan;
import com.allen.service.basic.productionplan.AddProductionPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 包路径：com.allen.service.basic.productionplan.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:35
 */
@Service
public class AddProductionPlanServiceImpl implements AddProductionPlanService {
    @Resource
    private ProductionPlanDao productionPlanDao;
    @Override
    public void addProductionPlan(ProductionPlan productionPlan) {
       ProductionPlan oldProductionPlan = null;//productionPlanDao.findByProductIdAndProductionDate(productionPlan.getProductId(),productionPlan.getProductionDate());
        if(oldProductionPlan!=null){
            oldProductionPlan.setDemandNum(oldProductionPlan.getDemandNum().add(productionPlan.getDemandNum()));
            oldProductionPlan.setProductionNum(oldProductionPlan.getProductionNum().add(productionPlan.getProductionNum()));
            oldProductionPlan.setPlanNum(oldProductionPlan.getPlanNum().add(productionPlan.getPlanNum()));
            productionPlanDao.save(oldProductionPlan);
        }else{
            productionPlanDao.save(productionPlan);
        }
    }
}
