package com.allen.service.basic.workgroup.impl;

import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.workgroup.FindWorkGroupForAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/2/21 0021.
 */
@Service
public class FindWorkGroupForAllServiceImpl implements FindWorkGroupForAllService {

    @Resource
    private WorkGroupDao workGroupDao;

    @Override
    public List<WorkGroup> find() throws Exception {
        return (List<WorkGroup>) workGroupDao.findAll();
    }
}
