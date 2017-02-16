package com.allen.service.basic.producttype.impl;

import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.FindProductTypeByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindProductTypeByIdServiceImpl implements FindProductTypeByIdService {

    @Resource
    private ProductTypeDao productTypeDao;

    @Override
    public ProductType find(long id) throws Exception {
        return productTypeDao.findOne(id);
    }
}
