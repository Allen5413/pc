package com.allen.service.user.usergroupuser;

import com.allen.entity.user.UserGroup;
import com.allen.entity.user.UserGroupUser;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface AddUserGroupUserService {
    public void addUserGroupUser(List<UserGroupUser> userGroupUsers,long userId)throws Exception;
}
