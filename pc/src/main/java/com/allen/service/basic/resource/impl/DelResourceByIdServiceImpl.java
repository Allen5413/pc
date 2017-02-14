package com.allen.service.basic.resource.impl;

import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.DelResourceByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelResourceByIdServiceImpl implements DelResourceByIdService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        List<Resource> childResources = resourceDao.findByParentId(id);
        resourceDao.delete(id);
        resourceDao.delete(childResources);
    }
}
