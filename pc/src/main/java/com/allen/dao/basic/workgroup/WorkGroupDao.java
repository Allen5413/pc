package com.allen.dao.basic.workgroup;

import com.allen.entity.basic.WorkGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkGroupDao extends CrudRepository<WorkGroup, Long> {

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
