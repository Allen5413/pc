package com.allen.service.basic.classgroup.impl;

import com.allen.dao.basic.classgroup.ClassGroupDao;
import com.allen.entity.basic.ClassGroup;
import com.allen.service.basic.classgroup.FindClassGroupByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindClassGroupByIdServiceImpl implements FindClassGroupByIdService {

    @Resource
    private ClassGroupDao classGroupDao;

    @Override
    public ClassGroup find(long id) throws Exception {
        return classGroupDao.findOne(id);
    }
}
