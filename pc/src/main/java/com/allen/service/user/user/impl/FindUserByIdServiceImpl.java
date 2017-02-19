package com.allen.service.user.user.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.user.user.FindUserDao;
import com.allen.dao.user.user.UserDao;
import com.allen.entity.user.User;
import com.allen.service.user.user.FindUserByIdService;
import com.allen.service.user.user.FindUserPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindUserByIdServiceImpl implements FindUserByIdService {

    @Autowired
    private UserDao userDao;
    @Override
    public User findUserById(long id) {
        return userDao.findById(id);
    }
}
