package com.allen.dao.user.usergroup;
import com.allen.entity.user.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lenovo on 2017/2/15.
 */
public interface UserGroupDao extends CrudRepository<UserGroup, Long> {
    /**
     * 功能：通过角色名称查询
     * @param name
     * @return
     */
    public List<UserGroup> findByName(String name);
}
