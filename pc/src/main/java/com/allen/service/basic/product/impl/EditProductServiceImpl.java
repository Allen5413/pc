package com.allen.service.basic.product.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.product.ProductDao;
import com.allen.dao.basic.productselfuse.ProductSelfUseDao;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductSelfUse;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.product.EditProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditProductServiceImpl implements EditProductService {

    @Resource
    private ProductDao productDao;
    @Resource
    private ProductSelfUseDao productSelfUseDao;
    @Override
    public void edit(Product product) throws Exception {

    }
}
