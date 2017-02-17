package com.allen.service.user.usergroupuser;

import com.allen.entity.user.UserGroupUser;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindUserGroupByUserIdService {
    public List<UserGroupUser> findUserGroupByUserId(long userId);
}
