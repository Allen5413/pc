package com.allen.dao.user.user;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.user.User;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Allen on 2017/1/4 0004.
 */
@Service
public class FindUserDao extends BaseQueryDao {

    /**
     * 分页查询信息
     * @param pageInfo
     * @param name
     * @param state
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, String name, Integer state)throws Exception{
        String[] tableNames = {"User"};
        String defaultWhere = "state > 0";
        LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>(2);
        paramsMap.put("name", StringUtil.isEmpty(name) ? "" : "%"+name+"%");
        paramsMap.put("state", state);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>(1);
        sortMap.put("id", true);
        String[] paramsIf = {">", "like", "="};
        return super.findPageByJpal(pageInfo, tableNames, defaultWhere, paramsMap, paramsIf, sortMap);
    }

    /**
     * 通过登录名和密码查询用户
     * @param loginName
     * @param pwd
     * @return
     * @throws Exception
     */
    public User findByLoginNameAndPwd(String loginName, String pwd)throws Exception{
        String fields = "u";
        String[] tableNames = {"User u"};
        LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
        paramsMap.put("loginName",loginName);
        paramsMap.put("pwd",pwd);
        paramsMap.put("state",0);
        String[] paramsIf = {"=", "=", ">"};
        return (User) super.findByHql(tableNames, fields, paramsMap, paramsIf, null, User.class);
    }
}
