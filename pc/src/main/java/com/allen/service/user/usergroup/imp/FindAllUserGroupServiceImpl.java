package com.allen.service.user.usergroup.imp;

import com.allen.dao.PageInfo;
import com.allen.dao.user.usergroup.UserGroupDao;
import com.allen.entity.user.UserGroup;
import com.allen.service.user.usergroup.FindAllUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindAllUserGroupServiceImpl implements FindAllUserGroupService {
    @Autowired
    private UserGroupDao userGroupDao;
    @Override
    public List<UserGroup> findAll(){
        return (List<UserGroup>)userGroupDao.findAll();
    }
}
