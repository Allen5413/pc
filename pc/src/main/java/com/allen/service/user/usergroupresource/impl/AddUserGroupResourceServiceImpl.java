package com.allen.service.user.usergroupresource.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.user.usergroupresource.UserGroupResourceDao;
import com.allen.entity.user.UserGroup;
import com.allen.entity.user.UserGroupResource;
import com.allen.service.user.usergroupresource.AddUserGroupResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class AddUserGroupResourceServiceImpl implements AddUserGroupResourceService{

    @Autowired
    private UserGroupResourceDao userGroupResourceDao;

    @Transactional
    @Override
    public void addUserGroupResource(List<UserGroupResource> userGroupResources,long userGroupId) {
       //获取原有的角色资源权限
        List<UserGroupResource> currentUserGroupResource = userGroupResourceDao.findByUserGroupId(userGroupId);
        if(userGroupResources==null&&currentUserGroupResource!=null){//删除所有权限
            for (UserGroupResource userGroupResource:currentUserGroupResource){
                userGroupResourceDao.delete(userGroupResource);
            }
        }else if(userGroupResources!=null&&(currentUserGroupResource==null||currentUserGroupResource.size()==0)){//新增权限
            for (UserGroupResource userGroupResource:userGroupResources){
                userGroupResourceDao.save(userGroupResource);
            }
        }else{
            boolean flag = true;
            for (UserGroupResource currentUserResource:currentUserGroupResource){//获取到删除的
                flag = true;
                for (UserGroupResource operateUserResource:userGroupResources){
                    if(currentUserResource.getResourceId()==operateUserResource.getResourceId()){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    userGroupResourceDao.delete(currentUserResource);
                }
            }
            for (UserGroupResource operateUserResource:userGroupResources){//获取到新增的
                flag = true;
                for (UserGroupResource currentUserResource:currentUserGroupResource){
                    if(currentUserResource.getResourceId()==operateUserResource.getResourceId()){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    userGroupResourceDao.save(operateUserResource);
                }
            }
        }
    }
}
