package com.allen.service.basic.workmode.impl;

import com.allen.dao.basic.workmode.WorkModeDao;
import com.allen.service.basic.workmode.DelWorkModeService;
import com.allen.service.basic.worktime.DelWorkTimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelWorkModeServiceImpl implements DelWorkModeService {

    @Resource
    private WorkModeDao workModeDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        workModeDao.delete(id);
    }
}
