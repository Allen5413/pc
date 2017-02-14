package com.allen.dao.basic.menu;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.Menu;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Allen on 2016/12/26 0026.
 */
@Service
public class FindMenuDao extends BaseQueryDao{

    /**
     * 分页查询信息
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String[] tableNames = {"Menu"};
        String[] paramsIf = {"like"};
        return super.findPageByJpal(pageInfo, tableNames, paramsMap, paramsIf, sortMap);
    }

    /**
     * 通过名称查询
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public Menu findByName(Map<String, Object> paramsMap)throws Exception{
        String fields = "m";
        String[] tableNames = {"Menu m"};
        return (Menu) super.findByHql(tableNames, fields, paramsMap, null, Menu.class);
    }
}
