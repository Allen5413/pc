package com.allen.dao.user.usergroup;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/2/15.
 */
public class FindUserGroupDao extends BaseQueryDao{
    /**
     * 分页查询信息
     * @param pageInfo
     * @param name
     * @param state
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, String name, Integer state)throws Exception{
        String[] tableNames = {"UserGroup"};
        String defaultWhere = "state > 0";
        LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>(2);
        paramsMap.put("name",new Object[]{StringUtil.isEmpty(name) ? "" : "%"+name+"%","like"});
        paramsMap.put("state", state);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>(1);
        sortMap.put("id", true);
        return super.findPageByJpal(pageInfo, tableNames, defaultWhere, paramsMap, sortMap);
    }
}
