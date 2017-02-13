package com.allen.service.basic.resource.impl;

import com.allen.dao.basic.resource.ResourceDao;
import com.allen.service.basic.resource.FindResourceByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allen.entity.basic.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindResourceByIdServiceImpl implements FindResourceByIdService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource find(long id) throws Exception {
        return resourceDao.findOne(id);
    }
}
