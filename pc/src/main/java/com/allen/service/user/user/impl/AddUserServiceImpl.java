package com.allen.service.user.user.impl;

import com.allen.dao.user.user.UserDao;
import com.allen.entity.user.User;
import com.allen.service.user.user.AddUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddUserServiceImpl implements AddUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void add(User user) throws Exception {
        userDao.save(user);
    }
}
