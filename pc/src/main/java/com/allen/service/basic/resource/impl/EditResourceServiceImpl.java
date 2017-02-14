package com.allen.service.basic.resource.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.resource.FindResourceDao;
import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.EditResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public void edit(Resource resource, List<Resource> buttons) throws Exception {
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
        //查找原有buttons
        List<Resource> oldButtons = findResourceDao.findButtonsByResourceId(resource.getId());
        for(Resource button : buttons){
            if(button.getId()==0){//保存
                resourceDao.save(button);
                continue;
            }
        }
        for (Resource oldButton:oldButtons){
            boolean hasButton = false;
            for(Resource button : buttons){
                if(button.getId()==oldButton.getId()){
                    hasButton = true;
                    oldButton.setName(button.getName());
                    oldButton.setButtonCode(button.getButtonCode());
                    oldButton.setOperator(button.getOperator());
                    oldButton.setOperateTime(button.getOperateTime());
                    resourceDao.save(oldButton);
                    break;
                }
            }
            if(!hasButton){
                resourceDao.delete(oldButton);
            }
        }
    }
}
