package com.allen.service.basic.producttype.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.AddProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddProductTypeServiceImpl implements AddProductTypeService {

    @Resource
    private ProductTypeDao productTypeDao;

    @Override
    public void add(ProductType productType) throws Exception {
        List list = productTypeDao.findByCode(productType.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = productTypeDao.findByName(productType.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        productTypeDao.save(productType);
    }
}
