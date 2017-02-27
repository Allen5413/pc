package com.allen.service.basic.worktime.impl;

import com.allen.dao.basic.worktime.WorkTimeDao;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.worktime.FindWorkTimeAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindWorkTimeAllServiceImpl implements FindWorkTimeAllService {

    @Resource
    private WorkTimeDao workTimeDao;

    @Override
    public List<WorkTime> findAll() throws Exception {
        return (List<WorkTime>) workTimeDao.findAll();
    }
}
