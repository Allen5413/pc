package com.allen.service.basic.workmode.impl;

import com.allen.dao.basic.workmode.WorkModeDao;
import com.allen.entity.basic.WorkMode;
import com.allen.service.basic.workmode.FindWorkModeByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindWorkModeByIdServiceImpl implements FindWorkModeByIdService {

    @Resource
    private WorkModeDao workModeDao;

    @Override
    public WorkMode find(long id) throws Exception {
        return workModeDao.findOne(id);
    }
}
