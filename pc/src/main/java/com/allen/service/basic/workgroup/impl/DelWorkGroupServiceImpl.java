package com.allen.service.basic.workgroup.impl;

import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.service.basic.workgroup.DelWorkGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelWorkGroupServiceImpl implements DelWorkGroupService {

    @Resource
    private WorkGroupDao workGroupDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        workGroupDao.delete(id);
    }
}
