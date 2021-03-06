package com.allen.dao.reportform;

import com.allen.dao.BaseQueryDao;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询产能排班报表
 * Created by Allen on 2017/3/20 0020.
 */
@Service
public class FindCapacityDao extends BaseQueryDao {
    public List<Map> findByFmaterialIds(Map<String, String> param)throws Exception{
        List<Object> paramsList = new ArrayList<Object>();
        String wgId = param.get("wgID");
        String cgId = param.get("cgId");
        String wcId = param.get("wcId");

        String sql = "select wg.name as wgName, wc.name as wcName, cg.name as cgName, wt.name as wtName, plu.production_date, wt.begin_time, wt.end_time, plu.add_time, plu.capacity, plu.work_time ";
        sql += "from produce_line_use plu, work_group_core wgc, work_group wg, work_core wc, class_group cg, work_time wt ";
        sql += "where plu.work_core_id = wgc.work_core_id and wgc.work_group_id = wg.id and wgc.work_core_id = wc.id and plu.work_team_id = cg.id and plu.work_time_id = wt.id ";
        if(!StringUtil.isEmpty(wgId)){
            sql += "and wg.id = ? ";
            paramsList.add(Long.parseLong(wgId));
        }
        if(!StringUtil.isEmpty(wcId)){
            sql += "and wc.id = ? ";
            paramsList.add(Long.parseLong(wcId));
        }
        if(!StringUtil.isEmpty(cgId)){
            sql += "and cg.id = ? ";
            paramsList.add(Long.parseLong(cgId));
        }
        sql += "GROUP BY wg.name, wc.name, cg.name, wt.name, plu.production_date, begin_time, end_time, add_time, capacity,plu.work_time  ";
        sql += "order by wg.name, wc.name, cg.name, wt.name, plu.production_date, wt.begin_time,plu.work_time ";

        List<Map> list = super.sqlQueryByNativeSqlToMap(sql.toString(), paramsList.toArray());
        return list;
    }
}
