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
        String fields = "p.*,pt.name as tName";
        String[] tableNames = {"product p, product_type pt"};
        String defaultWhere = "p.type = pt.id";
        return super.findPageByNativeSqlToMap(pageInfo, fields,defaultWhere,tableNames, paramsMap, sortMap);
    }
}
