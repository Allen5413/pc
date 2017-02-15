package com.allen.service.basic.resource.impl;
import com.allen.dao.basic.resource.FindResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.FindResourceByParentIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class FindResourceByParentIdServiceImpl implements FindResourceByParentIdService {
    @Autowired
    private FindResourceDao findResourceDao;

    @Override
    public List<Resource> findButtonResource(long id) throws Exception {
        return findResourceDao.findButtonsByResourceId(id);
    }
}
