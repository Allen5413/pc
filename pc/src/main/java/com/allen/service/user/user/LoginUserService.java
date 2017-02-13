package com.allen.service.user.user;

import com.allen.entity.user.User;

/**
 * Created by Allen on 2016/12/15.
 */
public interface LoginUserService {
    public User login(String loginName, String pwd)throws Exception;
}
