package com.allen.service.basic.product.impl;

import com.allen.dao.basic.product.FindProductDao;
import com.allen.dao.basic.product.ProductDao;
import com.allen.dao.basic.productselfuse.FindProductSelfUseDao;
import com.allen.dao.basic.productselfuse.ProductSelfUseDao;
import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.product.FindProductByIdService;
import com.allen.service.basic.producttype.FindProductTypeByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindProductByIdServiceImpl implements FindProductByIdService {

    @Resource
    private FindProductDao findProductDao;
    @Resource
    private FindProductSelfUseDao findProductSelfUseDao;
    @Override
    public Map<String,Object> find(long id) throws Exception {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("a.FMATERIALID",Long.valueOf(id));
        List<Map> products = findProductDao.findByMap(params,null);
        if(products!=null&&products.size()>0){
            Map result = products.get(0);
            params.clear();
            params.put("d.FMATERIALID",Long.valueOf(id));
            //查找产品组成
            result.put("selfMades",findProductSelfUseDao.findByMap(params,null));
            return result;
        }
        return null;
    }
}
