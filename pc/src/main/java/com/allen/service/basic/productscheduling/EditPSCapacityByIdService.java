package com.allen.service.basic.productscheduling;

import java.math.BigDecimal;

/**
 * Created by Allen on 2017/6/16 0016.
 */
public interface EditPSCapacityByIdService {
    public void edit(long id, BigDecimal count)throws Exception;
}
