package com.allen.service.basic.worktime.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.worktime.FindWorkTimeDao;
import com.allen.service.basic.worktime.FindWorkTimePageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindWorkTimePageServiceImpl implements FindWorkTimePageService {

    @Resource
    private FindWorkTimeDao findWorkTimeDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findWorkTimeDao.findPage(pageInfo, params, sortMap);
    }
}
