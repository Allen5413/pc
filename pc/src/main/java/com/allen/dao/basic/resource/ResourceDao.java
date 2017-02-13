package com.allen.dao.basic.resource;

import com.allen.entity.basic.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface ResourceDao extends CrudRepository<Resource, Long> {

    /**
     * 通过UserId查询一个用户所关联的资源
     * @param userId
     * @return
     */
    @Query("select r from Resource r, UserGroupResource ugr, UserGroupUser ugu where r.id = ugr.resourceId and ugr.userGroupId = ugu.userGroupId and ugu.userId = ?1")
    public List<Resource> findByUserId(long userId);
}
