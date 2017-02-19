package com.allen.service.user.user;

import com.allen.dao.PageInfo;
import com.allen.entity.user.User;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindUserByIdService {
    public User findUserById(long id);
}
