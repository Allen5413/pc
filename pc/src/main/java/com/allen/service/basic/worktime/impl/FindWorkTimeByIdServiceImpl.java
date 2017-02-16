package com.allen.service.basic.worktime.impl;

import com.allen.dao.basic.worktime.WorkTimeDao;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.worktime.FindWorkTimeByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindWorkTimeByIdServiceImpl implements FindWorkTimeByIdService {

    @Resource
    private WorkTimeDao workTimeDao;

    @Override
    public WorkTime find(long id) throws Exception {
        return workTimeDao.findOne(id);
    }
}
