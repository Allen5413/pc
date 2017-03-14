package com.allen.service.basic.product.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.dao.basic.product.ProductDao;
import com.allen.dao.basic.productselfuse.ProductSelfUseDao;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductSelfUse;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.product.EditProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditProductServiceImpl implements EditProductService {

    @Resource
    private FindProductDao findProductDao;
    @Resource
    private ProductDao productDao;

    @Override
    @Transactional
    public void edit(Product product) throws Exception {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("FMATERIALID",product.getFMATERIALID());
        List<Product> oldProductArr = findProductDao.find(params);
        if(oldProductArr!=null&&oldProductArr.size()>0){
            Product oldProduct = oldProductArr.get(0);
            oldProduct.setFSNO(product.getFSNO());
            productDao.save(oldProduct);
        }
    }
}
