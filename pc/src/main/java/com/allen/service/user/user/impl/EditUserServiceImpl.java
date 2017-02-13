package com.allen.service.user.user.impl;

import com.allen.dao.user.user.UserDao;
import com.allen.entity.user.User;
import com.allen.service.user.user.EditUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditUserServiceImpl implements EditUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void edit(User user) throws Exception {

        User oldUser = userDao.findOne(user.getId());
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setState(user.getState());
        oldUser.setRemark(user.getRemark());
        oldUser.setOperator(user.getOperator());
        oldUser.setOperateTime(user.getOperateTime());
        oldUser.setVersion(user.getVersion());
        userDao.save(oldUser);
    }
}
