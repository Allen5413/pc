package com.allen.service.user.usergroupresource.impl;

import com.allen.dao.user.usergroupresource.UserGroupResourceDao;
import com.allen.entity.user.UserGroup;
import com.allen.entity.user.UserGroupResource;
import com.allen.service.user.usergroupresource.FindResourceByGroupIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2017/2/16.
 */
@Service
public class FindResourceByGroupIdServiceImpl implements FindResourceByGroupIdService {
    @Autowired
    private UserGroupResourceDao userGroupResourceDao;
    @Override
    public List<UserGroupResource> findResourceByGroupId(long userGroupId) {
        return userGroupResourceDao.findByUserGroupId(userGroupId);
    }
}
