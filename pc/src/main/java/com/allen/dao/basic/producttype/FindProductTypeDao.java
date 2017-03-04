package com.allen.dao.basic.producttype;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.ProductType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/14 0014.
 */
@Service
public class FindProductTypeDao extends BaseQueryDao {

    /**
     * 分页查询信息
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String[] tableNames = {"ProductType"};
        return super.findPageByJpal(pageInfo, tableNames, paramsMap, sortMap);
    }

    /**
     * 功能:查询下拉列表中的产品信息
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public List<ProductType> find(Map<String,Object> paramsMap) throws Exception{
        String fields = "p";
        String[] tableNames = {"ProductType p"};
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.FNAME",false);
        return super.findListByHql(tableNames,fields,paramsMap,sortMap,ProductType.class);
    }
}
