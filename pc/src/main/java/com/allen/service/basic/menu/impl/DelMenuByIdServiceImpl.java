package com.allen.service.basic.menu.impl;

import com.allen.dao.basic.menu.MenuDao;
import com.allen.service.basic.menu.DelMenuByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelMenuByIdServiceImpl implements DelMenuByIdService {

    @Resource
    private MenuDao menuDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        menuDao.delete(id);
    }
}
