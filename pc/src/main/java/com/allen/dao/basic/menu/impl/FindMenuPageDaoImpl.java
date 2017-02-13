package com.allen.dao.basic.menu.impl;


import com.allen.dao.BaseQueryDao;
import com.allen.dao.FindPageByWhereDao;
import com.allen.dao.PageInfo;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 实现分页查询菜单信息接口
 * Created by Allen on 2015/4/28.
 */
@Service
public class FindMenuPageDaoImpl extends BaseQueryDao implements FindPageByWhereDao {
    @Override
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> paramsMap, Map<String, Boolean> sortMap) {
        PageInfo menuPageInfo = new PageInfo();
        menuPageInfo.setCurrentPage(pageInfo.getCurrentPage());
        menuPageInfo.setCountOfCurrentPage(pageInfo.getCountOfCurrentPage());

        StringBuilder sql = new StringBuilder("FROM Menu m where 1=1 ");
        String name = paramsMap.get("name");

        List<Object> param = new ArrayList<Object>();
        if(!StringUtil.isEmpty(name)){
            sql.append(" and m.name = ? ");
            param.add(name);
        }
        if(null != sortMap) {
            sql.append("order by ");
            int i = 0;
            for (Iterator it = sortMap.keySet().iterator(); it.hasNext(); ) {
                if(0 < i){
                    sql.append(",");
                }
                String key = it.next().toString();
                sql.append(key);
                sql.append(" ");
                sql.append(sortMap.get(key) ? "asc" : "desc");
                i++;
            }
        }
        menuPageInfo = super.pagedQueryByJpql(menuPageInfo, sql.toString(), param.toArray());
        return menuPageInfo;
    }
}
