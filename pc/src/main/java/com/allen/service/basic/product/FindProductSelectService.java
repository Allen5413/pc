package com.allen.service.basic.product;

import com.allen.entity.basic.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/2/22.
 */
public interface FindProductSelectService {
    public List<Product> find(Map<String,Object> queryParams) throws Exception;
}
