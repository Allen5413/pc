package com.allen.service.basic.workcore.impl;

import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.workcore.FindWorkCoreByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindWorkCoreByIdServiceImpl implements FindWorkCoreByIdService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    public WorkCore find(long id) throws Exception {
        return workCoreDao.findOne(id);
    }
}
