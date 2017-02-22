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
        Product oldProduct = productDao.findOne(product.getId());
        List list = productDao.findByCode(product.getCode());
        if(null != list && 0 < list.size() && !oldProduct.getCode().equals(product.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = productDao.findByName(product.getName());
        if(null != list && 0 < list.size() && !oldProduct.getName().equals(product.getName())){
            throw new BusinessException("名称已存在！");
        }
        oldProduct.setSelfMade(product.getSelfMade());
        oldProduct.setType(product.getType());
        oldProduct.setCode(product.getCode());
        oldProduct.setName(product.getName());
        oldProduct.setOperator(product.getOperator());
        oldProduct.setOperateTime(product.getOperateTime());
        productDao.save(oldProduct);
        //查找原有产品组成信息
        List<ProductSelfUse> currentProductSelfUses = productSelfUseDao.findByProductId(oldProduct.getId());
        if(currentProductSelfUses==null||currentProductSelfUses.size()==0){//添加新的
            if(product.getProductSelfUses()!=null&&product.getProductSelfUses().size()>0){
                for (ProductSelfUse productSelfUse:product.getProductSelfUses()){
                    productSelfUse.setProductId(oldProduct.getId());
                    productSelfUse.setCreator(oldProduct.getOperator());
                    productSelfUse.setCreatorId(oldProduct.getOperatorId());
                    productSelfUse.setOperatorId(oldProduct.getOperatorId());
                    productSelfUse.setOperator(oldProduct.getOperator());
                    productSelfUseDao.save(productSelfUse);
                }
            }
        }else if(product.getProductSelfUses()==null||product.getProductSelfUses().size()==0){
            if(currentProductSelfUses!=null&&currentProductSelfUses.size()>0) {//全部删除
                for (ProductSelfUse productSelfUse:currentProductSelfUses){
                    productSelfUseDao.delete(productSelfUse);
                }
            }
        }else{
            boolean flag = false;
            for (ProductSelfUse currentProduct:currentProductSelfUses){
                 flag = false;
                for (ProductSelfUse operateProduct:product.getProductSelfUses()){
                    if(currentProduct.getCode().equals(operateProduct.getCode())){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    productSelfUseDao.delete(currentProduct);
                }
            }
            for (ProductSelfUse operateProduct:product.getProductSelfUses()){
                flag = false;
                operateProduct.setOperator(product.getOperator());
                operateProduct.setOperatorId(product.getOperatorId());
                for (ProductSelfUse currentProduct:currentProductSelfUses){
                    if(currentProduct.getCode().equals(operateProduct.getCode())){
                        flag = true;
                        operateProduct.setCreator(currentProduct.getCreator());
                        operateProduct.setCreatorId(currentProduct.getCreatorId());
                        operateProduct.setCreateTime(currentProduct.getCreateTime());
                        break;
                    }
                }
                if(flag){
                    operateProduct.setCreator(product.getCreator());
                    operateProduct.setCreatorId(product.getCreatorId());
                }
                productSelfUseDao.save(operateProduct);
            }
        }
    }
}
