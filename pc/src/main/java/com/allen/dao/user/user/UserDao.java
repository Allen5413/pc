package com.allen.dao.user.user;

import com.allen.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Allen on 2016/12/15.
 */
public interface UserDao extends CrudRepository<User, Long> {
    public User findById(Long id);
}
