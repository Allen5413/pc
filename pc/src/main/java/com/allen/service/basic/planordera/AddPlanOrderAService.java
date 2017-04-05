package com.allen.service.basic.planordera;

import java.math.BigDecimal;

/**
 * Created by Allen on 2017/4/5 0005.
 */
public interface AddPlanOrderAService {
    public void add(long fId, BigDecimal FBASEYIELDQTY)throws Exception;
}
