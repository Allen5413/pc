package com.allen.service.basic.worktime;

import com.allen.entity.basic.WorkTime;

import java.util.List;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindWorkTimeAllService {
    public List<WorkTime> findAll()throws Exception;
}
