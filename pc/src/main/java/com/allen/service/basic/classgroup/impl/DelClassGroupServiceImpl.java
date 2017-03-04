package com.allen.service.basic.classgroup.impl;

import com.allen.dao.basic.classgroup.ClassGroupDao;
import com.allen.service.basic.classgroup.DelClassGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelClassGroupServiceImpl implements DelClassGroupService {

    @Resource
    private ClassGroupDao classGroupDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        classGroupDao.delete(id);
    }
}
