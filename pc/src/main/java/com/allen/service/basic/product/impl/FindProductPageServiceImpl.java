package com.allen.service.basic.product.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.service.basic.product.FindProductPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lenovo on 2017/2/21.
 */
@Service
public class FindProductPageServiceImpl implements FindProductPageService{
    @Autowired
    private FindProductDao findProductDao;
    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findProductDao.findPage(pageInfo,params,sortMap);
    }
}
