package com.allen.service.user.usergroupuser.impl;

import com.allen.dao.user.usergroupuser.UserGroupUserDao;
import com.allen.entity.user.UserGroupResource;
import com.allen.entity.user.UserGroupUser;
import com.allen.service.user.usergroupresource.FindResourceByGroupIdService;
import com.allen.service.user.usergroupuser.FindUserGroupByUserIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindUserGroupByUserIdServiceImpl implements FindUserGroupByUserIdService{

    @Autowired
    private UserGroupUserDao userGroupUserDao;
    @Override
    public List<UserGroupUser> findUserGroupByUserId(long userId) {
        return userGroupUserDao.findByUserId(userId);
    }
}
