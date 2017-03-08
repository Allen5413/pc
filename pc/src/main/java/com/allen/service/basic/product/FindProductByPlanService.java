package com.allen.service.basic.product;

import com.allen.entity.basic.PlanOrder;

import java.util.List;
import java.util.Map;

/**
 * 包路径：com.allen.service.basic.product
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-04 23:09
 */
public interface FindProductByPlanService {
    public List<PlanOrder> findProductByPlan();
}
