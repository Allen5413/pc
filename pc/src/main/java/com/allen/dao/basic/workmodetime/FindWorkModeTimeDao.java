package com.allen.dao.basic.workmodetime;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.WorkTime;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/14 0014.
 */
@Service
public class FindWorkModeTimeDao extends BaseQueryDao {

    /**
     * 分页查询信息
     * @param pageInfo
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPage(PageInfo pageInfo, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        String[] tableNames = {"WorkModeTime"};
        return super.findPageByJpal(pageInfo, tableNames, paramsMap, sortMap);
    }

    /**
     * 根据工作模式查询关联班次信息
     * @param workModeId
     * @return
     */
    public List<WorkTime> findByWorkModeId(long workModeId){
        String fields = "b";
        String[] tableNames = {"WorkModeTime a,WorkTime b"};
        String defaultWhere = "a.workTimeId = b.id";
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("b.code",false);
        Map<String,Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("a.workModeId",workModeId);
        return super.findListByHql(tableNames, fields, defaultWhere, paramsMap, sortMap,WorkTime.class);
    }
}
