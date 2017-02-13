package com.allen.service.user.user.impl;

import com.allen.dao.user.user.UserDao;
import com.allen.entity.user.User;
import com.allen.service.user.user.DelUserByIdService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelUserByIdServiceImpl implements DelUserByIdService {

    @Resource
    private UserDao userDao;

    @Override
    @Transactional
    public void del(long id, String loginName) throws Exception {
        User user = userDao.findOne(id);
        user.setState(User.STATE_DELETE);
        user.setOperator(loginName);
        user.setOperateTime(DateUtil.getLongNowTime());
        userDao.save(user);
    }
}
