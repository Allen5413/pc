package com.allen.service.user.usergroup;

import com.allen.dao.PageInfo;
import com.allen.entity.user.UserGroup;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindAllUserGroupService {
    public List<UserGroup> findAll();
}
