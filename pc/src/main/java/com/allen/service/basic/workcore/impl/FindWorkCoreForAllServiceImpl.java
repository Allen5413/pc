package com.allen.service.basic.workcore.impl;

import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.workcore.FindWorkCoreForAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/3/10.
 */
@Service
public class FindWorkCoreForAllServiceImpl implements FindWorkCoreForAllService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    public List<WorkCore> find() throws Exception {
        return (List<WorkCore>) workCoreDao.findAll();
    }
}
