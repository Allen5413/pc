package com.allen.dao.basic.resource;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.Resource;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class FindResourceDao extends BaseQueryDao {
    /**
     * 查询一个菜单下的资源
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public List<Resource> findByMenuId(Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String fields = "r";
        String[] tableNames = {"Resource r"};
        return  super.findListByHql(tableNames, fields, paramsMap, sortMap, Resource.class);
    }

    /**
     * 通过名称查询
     * @return
     * @throws Exception
     */
    public Resource findByNameAndMenuId(String name, long menuId)throws Exception{
        String fields = "r";
        String[] tableNames = {"Resource r"};
        LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
        paramsMap.put("name",name);
        paramsMap.put("menuId", menuId);
        return (Resource) super.findByHql(tableNames, fields, paramsMap, null, Resource.class);
    }
}
