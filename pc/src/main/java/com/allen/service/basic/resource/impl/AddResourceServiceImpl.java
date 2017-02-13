package com.allen.service.basic.resource.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.resource.FindResourceDao;
import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.AddResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddResourceServiceImpl implements AddResourceService {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private FindResourceDao findResourceDao;

    @Override
    public void add(Resource resource) throws Exception {
        Resource resourceByName = findResourceDao.findByNameAndMenuId(resource.getName(), resource.getMenuId());
        if(null != resourceByName){
            throw new BusinessException("名称已存在！");
        }
        resourceDao.save(resource);
    }
}
