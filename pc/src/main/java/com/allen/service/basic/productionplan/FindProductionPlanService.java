package com.allen.service.basic.productionplan;

import com.allen.entity.basic.ProductionPlan;

import java.util.List;
import java.util.Map;

/**
 * 包路径：com.allen.service.basic.productionplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:32
 */
public interface FindProductionPlanService {
    List<ProductionPlan> find(Map<String,Object> params);
}
