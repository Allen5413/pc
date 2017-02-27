package com.allen.service.basic.workmodetime;

import com.allen.dao.PageInfo;
import com.allen.entity.basic.WorkModeTime;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindWorkModeTimeByWorkModeIdService {
    public List<WorkModeTime> findWorkModeTimeByWorkModeId(long workModeId);
}
