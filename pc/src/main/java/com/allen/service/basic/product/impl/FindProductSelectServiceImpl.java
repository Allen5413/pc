package com.allen.service.basic.product.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.entity.basic.Product;
import com.allen.service.basic.product.FindProductPageService;
import com.allen.service.basic.product.FindProductSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/2/22.
 */
@Service
public class FindProductSelectServiceImpl implements FindProductSelectService {

    @Autowired
    private FindProductDao findProductDao;
    @Override
    public List<Product> find(Map<String, Object> queryParams) throws Exception {
        return findProductDao.find(queryParams);
    }
}
