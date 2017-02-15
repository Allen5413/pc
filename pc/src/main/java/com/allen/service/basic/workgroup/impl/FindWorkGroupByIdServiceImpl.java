package com.allen.service.basic.workgroup.impl;

import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.workgroup.FindWorkGroupByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindWorkGroupByIdServiceImpl implements FindWorkGroupByIdService {

    @Resource
    private WorkGroupDao workGroupDao;

    @Override
    public WorkGroup find(long id) throws Exception {
        return workGroupDao.findOne(id);
    }
}
