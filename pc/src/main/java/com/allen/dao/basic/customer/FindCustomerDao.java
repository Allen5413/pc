package com.allen.dao.basic.customer;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Allen on 2017/2/14 0014.
 */
@Service
public class FindCustomerDao extends BaseQueryDao {

    /**
     * 分页查询信息
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String fields = "c.FCUSTID as FCUSTID, c.FNUMBER as FNUMBER, cl.FNAME as FNAME";
        String[] tableNames = {"t_bd_customer c, t_bd_customer_l cl"};
        String defaultWhere = "c.FCUSTID = cl.FCUSTID";
        return super.findPageByNativeSqlToMap(pageInfo, fields, defaultWhere, tableNames, paramsMap, sortMap);
    }
}
