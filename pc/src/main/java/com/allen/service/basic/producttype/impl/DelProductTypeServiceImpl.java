package com.allen.service.basic.producttype.impl;

import com.allen.dao.basic.producttype.ProductTypeDao;
import com.allen.service.basic.producttype.DelProductTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelProductTypeServiceImpl implements DelProductTypeService {

    @Resource
    private ProductTypeDao productTypeDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        productTypeDao.delete(id);
    }
}
