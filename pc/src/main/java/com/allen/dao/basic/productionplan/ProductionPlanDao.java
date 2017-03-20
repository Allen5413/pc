package com.allen.dao.basic.productionplan;

import com.allen.entity.basic.ProductionPlan;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * 包路径：com.allen.dao.basic.productionplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:31
 */
public interface ProductionPlanDao extends CrudRepository<ProductionPlan,Long> {
    public ProductionPlan findByProductIdAndProductionDate(Long productId, Date productionDate);
}
