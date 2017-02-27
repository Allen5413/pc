package com.allen.dao.basic.workmodetime;

import com.allen.entity.basic.WorkMode;
import com.allen.entity.basic.WorkModeTime;
import com.allen.entity.basic.WorkTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface WorkModeTimeDao extends CrudRepository<WorkModeTime, Long> {

    /**
     * 通过工作模式id查询
     * @param workModeId
     * @return
     */
    public List<WorkModeTime> findByWorkModeId(long workModeId);

}
