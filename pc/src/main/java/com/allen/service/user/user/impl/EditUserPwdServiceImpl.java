package com.allen.service.user.user.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.user.user.FindUserDao;
import com.allen.dao.user.user.UserDao;
import com.allen.entity.user.User;
import com.allen.service.user.user.EditUserPwdService;
import com.allen.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/16.
 */
@Service
public class EditUserPwdServiceImpl implements EditUserPwdService {

    @Resource
    private UserDao userDao;
    @Resource
    private FindUserDao findUserDao;

    @Override
    public void edit(String loginName, String oldPwd, String newPwd) throws Exception {
        //查询登录用户的旧密码是否正确
        User user = findUserDao.findByLoginNameAndPwd(loginName, MD5Util.MD5(oldPwd));
        if(null == user || null == user.getId()){
            throw new BusinessException("旧密码错误");
        }
        user.setPwd(MD5Util.MD5(newPwd));
        userDao.save(user);
    }
}
