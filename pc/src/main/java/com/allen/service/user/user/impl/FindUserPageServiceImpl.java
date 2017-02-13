package com.allen.service.user.user.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.user.user.FindUserDao;
import com.allen.service.user.user.FindUserPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindUserPageServiceImpl implements FindUserPageService {

    @Resource
    private FindUserDao findUserDao;

    @Override
    public PageInfo find(PageInfo pageInfo, String name, Integer state) throws Exception {
        return findUserDao.findPage(pageInfo, name, state);
    }
}
