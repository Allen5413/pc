package com.allen.service.basic.menu.impl;

import com.allen.dao.basic.menu.MenuDao;
import com.allen.entity.basic.Menu;
import com.allen.service.basic.menu.FindMenuByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindMenuByIdServiceImpl implements FindMenuByIdService {

    @Resource
    private MenuDao menuDAO;

    @Override
    public Menu find(long id) throws Exception {
        return menuDAO.findOne(id);
    }
}
