package com.allen.dao;

import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindPageByWhereDao {
    public PageInfo findPageByWhere(PageInfo pageInfo, Map<String, String> paramsMap, Map<String, Boolean> sortMap);
}
