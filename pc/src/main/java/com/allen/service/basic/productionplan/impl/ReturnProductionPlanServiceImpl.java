package com.allen.service.basic.productionplan.impl;

import com.allen.dao.basic.planorder.PlanOrderDao;
import com.allen.dao.basic.productionplan.ProductionPlanDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProductionPlan;
import com.allen.service.basic.productionplan.ReturnProductionPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 2017/3/30 0030.
 */
@Service
public class ReturnProductionPlanServiceImpl implements ReturnProductionPlanService {

    @Resource
    private ProductionPlanDao productionPlanDao;
    @Resource
    private PlanOrderDao planOrderDao;

    @Override
    public void returnPlan(Date startDate, Date endDate) throws Exception {
        List<ProductionPlan> productionPlanList = productionPlanDao.findByProductionDateBetween(startDate, endDate);
        if(null != productionPlanList && 0 < productionPlanList.size()){
            for (ProductionPlan productionPlan : productionPlanList){
                long id = 0;
                PlanOrder planOrder = planOrderDao.findOne(id);
                planOrderDao.save(planOrder);
            }
        }
    }
}
