package com.allen.service.basic.product;

import com.allen.entity.basic.Product;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindProductByIdService {
    public Product find(long id)throws Exception;
}
