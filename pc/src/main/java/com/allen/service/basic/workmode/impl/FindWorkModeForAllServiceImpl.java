package com.allen.service.basic.workmode.impl;

import com.allen.dao.basic.workmode.WorkModeDao;
import com.allen.entity.basic.WorkMode;
import com.allen.service.basic.workmode.FindWorkModeForAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/2/28.
 */
@Service
public class FindWorkModeForAllServiceImpl implements FindWorkModeForAllService {

    @Resource
    private WorkModeDao workModeDao;

    @Override
    public List<WorkMode> find() throws Exception {
        return (List<WorkMode>) workModeDao.findAll();
    }
}
