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
        ProductType oldProductType = productTypeDao.findOne(productType.getFPKID());
        oldProductType.setFNAME(productType.getFNAME());
        oldProductType.setFDESCRIPTION(productType.getFDESCRIPTION());
        productTypeDao.save(oldProductType);
    }
}
