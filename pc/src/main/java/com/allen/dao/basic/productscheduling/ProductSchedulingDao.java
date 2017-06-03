package com.allen.dao.basic.productscheduling;

import com.allen.entity.basic.ProductScheduling;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * 包路径：com.allen.dao.basic.productionplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:31
 */
public interface ProductSchedulingDao extends CrudRepository<ProductScheduling,Long> {
    public List<ProductScheduling> findByProductionDateBetween(Date start, Date end);
}
