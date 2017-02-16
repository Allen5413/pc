package com.allen.service.user.usergroup.imp;

import com.allen.base.exception.BusinessException;
import com.allen.dao.user.usergroup.UserGroupDao;
import com.allen.entity.user.UserGroup;
import com.allen.service.user.usergroup.AddUserGroupService;
import com.allen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class AddUserGroupServiceImpl implements AddUserGroupService {
    @Autowired
    private UserGroupDao userGroupDao;

    /**
     * 功能：添加角色信息
     * @param userGroup
     * @throws Exception
     */
    public UserGroup addUserGroup(UserGroup userGroup)throws Exception{
        if(userGroup==null|| StringUtil.isEmpty(userGroup.getName())){
            throw new BusinessException("参数错误");
        }
        List<UserGroup> userGroups = userGroupDao.findByName(userGroup.getName());
        if(userGroups!=null&&userGroups.size()>0){
            throw new BusinessException("角色名称已存在");
        }
        return userGroupDao.save(userGroup);
    }
}
