package com.allen.service.basic.classgroup.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.classgroup.FindClassGroupDao;
import com.allen.service.basic.classgroup.FindClassGroupPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindClassGroupPageServiceImpl implements FindClassGroupPageService {

    @Resource
    private FindClassGroupDao findClassGroupDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findClassGroupDao.findPage(pageInfo, params, sortMap);
    }
}
