package com.allen.dao.basic.producelineuse;

import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductionPlan;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * 包路径：com.allen.dao.basic.producelineuse
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-07 20:52
 */
public interface ProduceLineUseDao extends CrudRepository<ProduceLineUse,Long> {
    public List<ProduceLineUse> findByProductionDateBetween(Date start, Date end);
    public List<ProduceLineUse> findByProductionDateAndProductId(Date start,long productId);
}
