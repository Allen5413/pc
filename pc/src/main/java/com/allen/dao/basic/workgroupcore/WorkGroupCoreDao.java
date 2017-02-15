package com.allen.dao.basic.workgroupcore;

import com.allen.entity.basic.WorkGroupCore;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkGroupCoreDao extends CrudRepository<WorkGroupCore, Long> {

    /**
     * 删除一个工作中心的所有关联
     * @param workCoreId
     * @throws Exception
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from work_group_core where work_core_id = ?1")
    public void delByWorkCoreId(long workCoreId)throws Exception;

    /**
     * 删除一个工作组的所有关联
     * @param workGroupId
     * @throws Exception
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from work_group_core where work_group_id = ?1")
    public void delByWorkGroupId(long workGroupId)throws Exception;
}
