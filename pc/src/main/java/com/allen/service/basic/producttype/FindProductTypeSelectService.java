package com.allen.service.basic.producttype;

import com.allen.dao.PageInfo;
import com.allen.entity.basic.ProductType;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindProductTypeSelectService {
    public List<ProductType> find();
}
