package com.allen.dao.basic.workcore;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by Allen on 2017/2/14 0014.
 */
@Service
public class FindWorkCoreDao extends BaseQueryDao {

    /**
     * 分页查询信息
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String[] tableNames = {"WorkCore wc"};
        return super.findPageByJpal(pageInfo, tableNames, paramsMap, sortMap);
    }

    /**
     * 分页查询信息（当查询条件选择了工作组的时候）
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByWgId(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String fields = "wc";
        String[] tableNames = {"WorkCore wc, WorkGroupCore wgc"};
        String defaultWhere = "wc.id = wgc.workCoreId";
        return super.findPageByJpal(pageInfo, fields, tableNames, defaultWhere, paramsMap, sortMap);
    }

    /**
     * 分页查询信息（当查询条件选择了生产线的时候）
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByPlId(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String fields = "wc";
        String[] tableNames = {"WorkCore wc, ProduceLineCore plc"};
        String defaultWhere = "wc.id = plc.workCoreId";
        return super.findPageByJpal(pageInfo, fields, tableNames, defaultWhere, paramsMap, sortMap);
    }

    /**
     * 分页查询信息（当查询条件选择了工作组和生产线的时候）
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByWgIdAndPlId(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String fields = "wc";
        String[] tableNames = {"WorkCore wc, WorkGroupCore wgc, ProduceLineCore plc"};
        String defaultWhere = "wc.id = wgc.workCoreId and wc.id = plc.workCoreId";
        return super.findPageByJpal(pageInfo, fields, tableNames, defaultWhere, paramsMap, sortMap);
    }
}
