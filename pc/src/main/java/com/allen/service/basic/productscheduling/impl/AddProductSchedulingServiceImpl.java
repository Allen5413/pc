package com.allen.service.basic.productscheduling.impl;

import com.allen.dao.basic.productionplan.ProductionPlanDao;
import com.allen.dao.basic.productscheduling.ProductSchedulingDao;
import com.allen.entity.basic.ProductScheduling;
import com.allen.entity.basic.ProductionPlan;
import com.allen.service.basic.productionplan.AddProductionPlanService;
import com.allen.service.basic.productscheduling.AddProductSchedulingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 包路径：com.allen.service.basic.productionplan.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:35
 */
@Service
public class AddProductSchedulingServiceImpl implements AddProductSchedulingService {
    @Resource
    private ProductSchedulingDao productSchedulingDao;
    @Override
    public void addProductScheduling(ProductScheduling productScheduling) {
        productSchedulingDao.save(productScheduling);
    }
}
