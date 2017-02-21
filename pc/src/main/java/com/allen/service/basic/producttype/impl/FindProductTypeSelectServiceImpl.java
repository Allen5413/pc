package com.allen.service.basic.producttype.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.producttype.FindProductTypeDao;
import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.FindProductTypePageService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindProductTypeSelectServiceImpl implements FindProductTypeSelectService {

    @Autowired
    private ProductTypeDao productTypeDao;
    @Override
    public List<ProductType> find(){
        return (List<ProductType>) productTypeDao.findAll();
    }
}
