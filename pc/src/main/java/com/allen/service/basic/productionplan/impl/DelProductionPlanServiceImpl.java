package com.allen.service.basic.productionplan.impl;

import com.allen.dao.basic.productionplan.ProductionPlanDao;
import com.allen.entity.basic.ProductionPlan;
import com.allen.service.basic.productionplan.DelProductionPlanService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.allen.service.basic.productionplan.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-21 22:04
 */
@Service
public class DelProductionPlanServiceImpl implements DelProductionPlanService {
    @Resource
    private ProductionPlanDao productionPlanDao;
    @Override
    public void delProductionPlan(String start, String end,long fMaterialId) {
        List<ProductionPlan> productionPlans = null;
        if(fMaterialId==0){
            productionPlans = productionPlanDao.findByProductionDateBetween(
                    DateUtil.getFormatDate(start,DateUtil.shortDatePattern),
                    DateUtil.getFormatDate(end,DateUtil.shortDatePattern));
        }else{
            productionPlans = productionPlanDao.findByProductionDateAndProductId(
                    DateUtil.getFormatDate(start,DateUtil.shortDatePattern),fMaterialId);
        }
        if(productionPlans!=null&&productionPlans.size()>0){
            productionPlanDao.delete(productionPlans);
        }
    }
}
