package com.allen.dao.user.usergroupresource;

import com.allen.entity.user.UserGroupResource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lenovo on 2017/2/15.
 */
public interface UserGroupResourceDao extends CrudRepository<UserGroupResource,Long> {
    /**
     * 功能：根据用户组id获取资源信息
     * @param userGroupId
     * @return
     */
    public List<UserGroupResource> findByUserGroupId(long userGroupId);
}
