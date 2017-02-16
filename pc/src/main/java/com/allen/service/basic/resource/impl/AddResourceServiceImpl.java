package com.allen.service.basic.resource.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.resource.FindResourceDao;
import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.AddResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public void add(Resource resource, List<Resource> buttons) throws Exception {
        Resource resourceByName = findResourceDao.findByNameAndMenuId(resource.getName(), resource.getMenuId());
        if(null != resourceByName){
            throw new BusinessException("名称已存在！");
        }
        resource = resourceDao.save(resource);
        if(buttons!=null){
            for (Resource button:buttons){
                button.setParentId(resource.getId());
                resourceDao.save(button);
            }
        }
    }
}
