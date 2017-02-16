package com.allen.service.user.usergroupresource;

import com.allen.entity.user.UserGroup;
import com.allen.entity.user.UserGroupResource;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindResourceByGroupIdService {
    public List<UserGroupResource> findResourceByGroupId(long userGroupId);
}
