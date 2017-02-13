package com.allen.service.user.user.impl;

import com.allen.dao.user.user.FindUserDao;
import com.allen.entity.user.User;
import com.allen.service.user.user.LoginUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15.
 */
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Resource
    private FindUserDao findUserDao;

    @Override
    public User login(String loginName, String pwd) throws Exception {
        User user = findUserDao.findByLoginNameAndPwd(loginName, pwd);
        return user;
    }
}
