package com.allen.dao.basic.workgroup;

import com.allen.entity.basic.WorkGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkGroupDao extends CrudRepository<WorkGroup, Long> {

    /**
     * 通过工作中心id查询工作组信息
     * 查询结果的工作中心id字段，如果有值说明有关联，没有值说明没有关联
     * @param workCoreId
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "SELECT wg.id, wg. CODE, wg. NAME, wgc.work_core_id " +
            "FROM work_group wg LEFT JOIN work_group_core wgc ON wg.id = wgc.work_group_id AND wgc.work_core_id = ?1 " +
            "ORDER BY wg.code")
    public List<Object[]> findWorkGroupAndWorkCoreIdByWorkCoreId(long workCoreId)throws Exception;

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List<WorkGroup> findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List<WorkGroup> findByName(String name);
}
