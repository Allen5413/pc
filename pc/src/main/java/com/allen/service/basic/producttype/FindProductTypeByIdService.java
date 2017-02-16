package com.allen.service.basic.producttype;

import com.allen.entity.basic.ProductType;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindProductTypeByIdService {
    public ProductType find(long id)throws Exception;
}
