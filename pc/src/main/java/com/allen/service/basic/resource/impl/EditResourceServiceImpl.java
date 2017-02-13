package com.allen.service.basic.resource.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.resource.FindResourceDao;
import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.EditResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditResourceServiceImpl implements EditResourceService {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private FindResourceDao findResourceDao;

    @Override
    public void edit(Resource resource) throws Exception {
        Resource resourceByName = findResourceDao.findByNameAndMenuId(resource.getName(), resource.getMenuId());
        if(null != resourceByName && resourceByName.getId() != resource.getId()){
            throw new BusinessException("名称已存在！");
        }
        Resource oldResource = resourceDao.findOne(resource.getId());
        oldResource.setName(resource.getName());
        oldResource.setUrl(resource.getUrl());
        oldResource.setRemark(resource.getRemark());
        oldResource.setOperator(resource.getOperator());
        oldResource.setOperateTime(resource.getOperateTime());
        oldResource.setVersion(resource.getVersion());
        resourceDao.save(oldResource);
    }
}
