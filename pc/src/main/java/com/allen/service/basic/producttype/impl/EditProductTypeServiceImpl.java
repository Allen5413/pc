package com.allen.service.basic.producttype.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.EditProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditProductTypeServiceImpl implements EditProductTypeService {

    @Resource
    private ProductTypeDao productTypeDao;

    @Override
    public void edit(ProductType productType) throws Exception {
        ProductType oldProductType = productTypeDao.findOne(productType.getId());
        List list = productTypeDao.findByCode(productType.getCode());
        if(null != list && 0 < list.size() && !oldProductType.getCode().equals(productType.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = productTypeDao.findByName(productType.getName());
        if(null != list && 0 < list.size() && !oldProductType.getName().equals(productType.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldProductType.setCode(productType.getCode());
        oldProductType.setName(productType.getName());
        oldProductType.setOperator(productType.getOperator());
        oldProductType.setOperateTime(productType.getOperateTime());
        productTypeDao.save(oldProductType);
    }
}
