package com.allen.service.basic.resource.impl;

import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.FindResourceByUserIdService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindResourceByUserIdServiceImpl implements FindResourceByUserIdService {

    @javax.annotation.Resource
    private ResourceDao resourceDAO;

    @Override
    public List<Resource> find(long userId) throws Exception {
        return resourceDAO.findByUserId(userId);
    }
}
