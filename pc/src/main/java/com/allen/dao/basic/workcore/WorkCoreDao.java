package com.allen.dao.basic.workcore;

import com.allen.entity.basic.WorkCore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkCoreDao extends CrudRepository<WorkCore, Long> {

    /**
     * 通过工作组id查询工作中心信息
     * 查询结果的工作组id字段，如果有值说明有关联，没有值说明没有关联
     * @param workCoreId
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "SELECT wc.id, wc.code, wc.name, wgc.work_group_id " +
            "FROM work_core wc LEFT JOIN work_group_core wgc ON wc.id = wgc.work_core_id AND wgc.work_group_id = ?1 " +
            "ORDER BY wc.code")
    public List<Object[]> findWorkGroupAndWorkGroupIdByWorkGroupId(long workCoreId)throws Exception;

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List findByName(String name);
}
