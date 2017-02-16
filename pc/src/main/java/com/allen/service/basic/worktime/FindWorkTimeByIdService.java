package com.allen.service.basic.worktime;

import com.allen.entity.basic.WorkTime;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindWorkTimeByIdService {
    public WorkTime find(long id)throws Exception;
}
