package com.allen.dao.reportform;

import com.allen.dao.BaseQueryDao;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询工作组排班表
 * Created by Allen on 2017/4/19 0019.
 */
@Service
public class FindWorkGroupSchedulDao extends BaseQueryDao {

    /**
     * 查询产品信息
     * @param param
     * @return
     * @throws Exception
     */
    public List<Map> findProduct(Map<String, String> param)throws Exception{
        List<Object> paramsList = new ArrayList<Object>();

        String startDate = param.get("startDate");
        String endDate = param.get("endDate");
        String wgId = param.get("wgId");

        paramsList.add(startDate);
        paramsList.add(endDate);
        paramsList.add(Long.parseLong(wgId));

        String sql = "select plu.product_id, pp.product_name, pp.product_no, pp.plan_total_num, pp.stock_num  ";
        sql += "FROM produce_line_use plu, work_group_core wgc, production_plan pp ";
        sql += "where and wgc.work_core_id = plu.work_core_id and plu.product_id = pp.product_id ";
        sql += "plu.production_date BETWEEN ? AND ? and wgc.work_group_id = ? ";
        sql += "group bycar plu.product_id, pp.product_name, pp.product_no, pp.plan_total_num, pp.stock_num  ";
        sql += "GROUP BY wg.name, wc.name, cg.name, wt.name, plu.production_date, begin_time, end_time, add_time, capacity,plu.work_time  ";
        sql += "order by wg.name, wc.name, cg.name, wt.name, plu.production_date, wt.begin_time,plu.work_time ";

        List<Map> list = super.sqlQueryByNativeSqlToMap(sql.toString(), paramsList.toArray());
        return list;
    }
}
