package com.allen.dao.user.usergroupuser;


import com.allen.entity.user.UserGroupUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lenovo on 2017/2/16.
 */
public interface UserGroupUserDao extends CrudRepository<UserGroupUser,Long> {
    /**
     * 功能：根据用户id查询角色信息
     * @param userId
     * @return
     */
    public List<UserGroupUser> findByUserId(long userId);
}
