package com.allen.dao.basic.workmode;

import com.allen.entity.basic.WorkMode;
import com.allen.entity.basic.WorkTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkModeDao extends CrudRepository<WorkMode, Long> {

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List<WorkMode> findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List<WorkMode> findByName(String name);
}
