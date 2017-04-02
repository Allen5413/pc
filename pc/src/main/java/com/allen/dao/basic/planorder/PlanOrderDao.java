package com.allen.dao.basic.planorder;

import com.allen.entity.basic.PlanOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 2017/3/30 0030.
 */
public interface PlanOrderDao  extends CrudRepository<PlanOrder, Long> {

}
