package com.allen.service.basic.productscheduling;

import com.allen.entity.basic.ProductScheduling;

/**
 * Created by Allen on 2017/6/16 0016.
 */
public interface FindPSByIdService {
    public ProductScheduling findById(long id)throws Exception;
}
