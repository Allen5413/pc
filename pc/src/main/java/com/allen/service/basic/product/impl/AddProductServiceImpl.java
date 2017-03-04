package com.allen.service.basic.product.impl;
import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.product.ProductDao;
import com.allen.dao.basic.productselfuse.ProductSelfUseDao;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductSelfUse;
import com.allen.service.basic.product.AddProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddProductServiceImpl implements AddProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductSelfUseDao productSelfUseDao;
    @Override
    @Transactional
    public void add(Product product) throws Exception {

    }
}
