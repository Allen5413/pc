package com.allen.service.basic.workmode.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.workmode.FindWorkModeDao;
import com.allen.dao.basic.worktime.FindWorkModeTimeDao;
import com.allen.service.basic.workmode.FindWorkModePageService;
import com.allen.service.basic.worktime.FindWorkTimePageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindWorkModePageServiceImpl implements FindWorkModePageService {

    @Resource
    private FindWorkModeDao findWorkModeDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findWorkModeDao.findPage(pageInfo, params, sortMap);
    }
}
