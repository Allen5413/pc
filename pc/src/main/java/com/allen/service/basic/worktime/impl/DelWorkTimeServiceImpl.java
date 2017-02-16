package com.allen.service.basic.worktime.impl;

import com.allen.dao.basic.worktime.WorkTimeDao;
import com.allen.service.basic.worktime.DelWorkTimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelWorkTimeServiceImpl implements DelWorkTimeService {

    @Resource
    private WorkTimeDao workTimeDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        workTimeDao.delete(id);
    }
}
