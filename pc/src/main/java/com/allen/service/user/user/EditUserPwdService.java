package com.allen.service.user.user;

/**
 * Created by Allen on 2017/2/16.
 */
public interface EditUserPwdService {
    public void edit(String loginName, String oldPwd, String newPwd)throws Exception;
}
