package com.allen.service.basic.resource.impl;

import com.allen.dao.basic.resource.FindResourceDao;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.FindResourceByMenuIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class FindResourceByMenuIdServiceImpl implements FindResourceByMenuIdService {

    @Autowired
    private FindResourceDao findResourceDao;

    @Override
    public List<Resource> find(Map<String, Object> paramsMap, Map<String, Boolean> sortMap) throws Exception {
        return findResourceDao.findByMenuId(paramsMap, sortMap);
    }
}
