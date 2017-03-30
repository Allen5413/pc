package com.allen.service.basic.productionplan;

import java.util.Date;

/**
 * Created by Allen on 2017/3/30 0030.
 */
public interface ReturnProductionPlanService {
    public void returnPlan(Date startDate, Date endDate)throws Exception;
}
