package com.allen.service.basic.workgroup.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.workgroup.FindWorkGroupDao;
import com.allen.service.basic.workgroup.FindWorkGroupPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindWorkGroupPageServiceImpl implements FindWorkGroupPageService {

    @Resource
    private FindWorkGroupDao findWorkGroupDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findWorkGroupDao.findPage(pageInfo, params, sortMap);
    }
}
