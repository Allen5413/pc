package com.allen.service.basic.product;

import com.allen.entity.pojo.product.ProductBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
public interface FindProductByPlIdAndWcIdService {
    public List<ProductBean> find(Map<String,Object> queryParams) throws Exception;
}
