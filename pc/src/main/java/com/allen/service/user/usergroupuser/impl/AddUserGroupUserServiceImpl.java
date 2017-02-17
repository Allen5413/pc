package com.allen.service.user.usergroupuser.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.user.usergroup.UserGroupDao;
import com.allen.dao.user.usergroupuser.UserGroupUserDao;
import com.allen.entity.user.UserGroup;
import com.allen.entity.user.UserGroupResource;
import com.allen.entity.user.UserGroupUser;
import com.allen.service.user.usergroup.AddUserGroupService;
import com.allen.service.user.usergroupuser.AddUserGroupUserService;
import com.allen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class AddUserGroupUserServiceImpl implements AddUserGroupUserService {
    @Autowired
    private UserGroupUserDao userGroupUserDao;

    /**
     * 功能：添加角色信息
     * @param userGroupUsers
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void addUserGroupUser(List<UserGroupUser> userGroupUsers,long userId)throws Exception{
        //获取现有的用户角色信息
        List<UserGroupUser> currentUserGroupUsers = userGroupUserDao.findByUserId(userId);
        if(userGroupUsers==null&&currentUserGroupUsers!=null){//删除所有角色信息
            for (UserGroupUser userGroupUser:currentUserGroupUsers){
                userGroupUserDao.delete(userGroupUser);
            }
        }else if(userGroupUsers!=null&&(currentUserGroupUsers==null||currentUserGroupUsers.size()==0)){//新增角色信息
            for (UserGroupUser userGroupUser:userGroupUsers){
                userGroupUserDao.save(userGroupUser);
            }
        }else{
            boolean flag = true;
            for (UserGroupUser currentUserGroupUser:currentUserGroupUsers){//获取到删除的
                flag = true;
                for (UserGroupUser userGroupUser:userGroupUsers){
                    if(currentUserGroupUser.getUserGroupId()==userGroupUser.getUserGroupId()){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    userGroupUserDao.delete(currentUserGroupUser);
                }
            }
            for (UserGroupUser userGroupUser:userGroupUsers){//获取到新增的
                flag = true;
                for (UserGroupUser currentUserGroupUser:currentUserGroupUsers){
                    if(currentUserGroupUser.getUserGroupId()==userGroupUser.getUserGroupId()){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    userGroupUserDao.save(userGroupUser);
                }
            }
        }
    }
}
