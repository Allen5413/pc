package com.allen.service.basic.workcore.impl;

import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.service.basic.workcore.DelWorkCoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelWorkCoreServiceImpl implements DelWorkCoreService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        workCoreDao.delete(id);
    }
}
