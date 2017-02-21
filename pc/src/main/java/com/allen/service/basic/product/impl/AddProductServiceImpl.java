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
        //查询是否存在编码
        List list = productDao.findByCode(product.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = productDao.findByName(product.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        //保存产品信息
        Product newProduct = productDao.save(product);
        if(product.getProductSelfUses()!=null&&product.getProductSelfUses().size()>0){
            for (ProductSelfUse productSelfUse:product.getProductSelfUses()){
                productSelfUse.setProductId(newProduct.getId());
                productSelfUse.setCreator(newProduct.getCreator());
                productSelfUse.setCreatorId(newProduct.getCreatorId());
                productSelfUse.setOperatorId(newProduct.getOperatorId());
                productSelfUse.setOperator(newProduct.getOperator());
                productSelfUseDao.save(productSelfUse);
            }
        }
    }
}
