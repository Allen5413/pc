package com.allen.dao.basic.product;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lenovo on 2017/2/21.
 */
@Service
public class FindProductDao extends BaseQueryDao {
    /**
     * 分页查询信息
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String[] tableNames = {"Product"};
        return super.findPageByJpal(pageInfo, tableNames, paramsMap, sortMap);
    }
}
