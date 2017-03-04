package com.allen.service.basic.producttype.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.dao.basic.producttype.FindProductTypeDao;
import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.FindProductTypePageService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindProductTypeSelectServiceImpl implements FindProductTypeSelectService {

    @Autowired
    private FindProductTypeDao findProductTypeDao;
    @Override
    public List<ProductType> find(){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("FLOCALEID",Long.valueOf(2052));
        try {
            return (List<ProductType>) findProductTypeDao.find(params);
        } catch (Exception e) {
            return new ArrayList<ProductType>();
        }
    }
}
