package com.allen.service.basic.product.impl;

import com.allen.dao.basic.product.ProductDao;
import com.allen.dao.basic.productselfuse.ProductSelfUseDao;
import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.product.FindProductByIdService;
import com.allen.service.basic.producttype.FindProductTypeByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindProductByIdServiceImpl implements FindProductByIdService {

    @Resource
    private ProductDao productDao;
    @Resource
    private ProductSelfUseDao productSelfUseDao;
    @Override
    public Product find(long id) throws Exception {
        Product product = productDao.findOne(id);;
        product.setProductSelfUses(productSelfUseDao.findByProductId(product.getId()));
        return product;
    }
}
