package com.allen.dao.basic.workcore;

import com.allen.entity.basic.WorkCore;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkCoreDao extends CrudRepository<WorkCore, Long> {

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
