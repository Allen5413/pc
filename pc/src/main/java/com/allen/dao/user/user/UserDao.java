package com.allen.dao.user.user;

import com.allen.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Allen on 2016/12/15.
 */
public interface UserDao extends CrudRepository<User, Long> {

    /**
     * 用户登录验证查询
     * @param loginName
     * @param pwd
     * @return
     */
    @Query("from User where loginName = ?1 and pwd = ?2")
    public User findByLoginNameAndPwd(String loginName, String pwd);
}
