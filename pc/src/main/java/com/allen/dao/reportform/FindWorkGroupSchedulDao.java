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
        String name = param.get("name");
        String coreCode = param.get("coreCode");

        paramsList.add(startDate+" 00:00:00");
        paramsList.add(endDate+" 23:59:59");


        String sql = "SELECT ps.id, ps.product_name, ps.product_no, ps.plan_capacity, ps.stock_num ";
//        sql += "FROM product_scheduling ps, work_group_core wgc, work_core wc ";
//        sql += "where ps.work_core_code = wc.code and wc.id = wgc.work_core_id and ps.production_date BETWEEN ? and ? ";
        sql += "FROM product_scheduling ps ";
        sql += "where ps.production_date BETWEEN ? and ? ";
//        if(!StringUtil.isEmpty(wgId)) {
//            paramsList.add(Long.parseLong(wgId));
//            sql += "and wgc.work_group_id = ? ";
//        }
        if(!StringUtil.isEmpty(name)) {
            paramsList.add("%"+name+"%");
            sql += "and ps.product_name = ? ";
        }
        if(!StringUtil.isEmpty(coreCode)) {
            paramsList.add(coreCode);
            sql += "and ps.work_core_code = ? ";
        }
        sql += "group by ps.product_name, ps.proc_code, ps.plan_capacity, ps.stock_num ";
        sql += "order by ps.proc_code";

        List<Map> list = super.sqlQueryByNativeSqlToMap(sql.toString(), paramsList.toArray());
        return list;
    }

    /**
     * 查询产品每天生产信息
     * @param param
     * @return
     * @throws Exception
     */
    public List<Map> findProductDetail(Map<String, String> param)throws Exception{
        List<Object> paramsList = new ArrayList<Object>();

        String date = param.get("date");
        String wgId = param.get("wgId");
        String name = param.get("name");
        String coreCode = param.get("coreCode");
        String productNo = param.get("productNo");
        String planCapacity = param.get("planCapacity");
        String stock = param.get("stock");


        String sql = "SELECT ps.id, ps.capacity, ps.work_core_name, ps.work_class_name, ps.work_time_start, ps.work_time_end, ps.work_time ";
//        sql += "FROM product_scheduling ps, work_group_core wgc, work_core wc ";
//        sql += "where ps.work_core_code = wc.code and wc.id = wgc.work_core_id and ps.production_date BETWEEN ? and ? and ps.product_name = ? and ps.product_no = ? and ps.plan_capacity = ? and ps.stock_num = ? ";
        sql += "FROM product_scheduling ps ";
        sql += "where ps.production_date BETWEEN ? and ? and ps.product_name = ? and ps.product_no = ? and ps.plan_capacity = ? and ps.stock_num = ? ";

        paramsList.add(date+" 00:00:00");
        paramsList.add(date+" 23:59:59");
        paramsList.add(name);
        paramsList.add(productNo);
        paramsList.add(Double.parseDouble(planCapacity));
        paramsList.add(Double.parseDouble(stock));

        if(!StringUtil.isEmpty(wgId)) {
            paramsList.add(Long.parseLong(wgId));
            sql += "and wgc.work_group_id = ? ";
        }
        if(!StringUtil.isEmpty(coreCode)) {
            paramsList.add(coreCode);
            sql += "and ps.work_core_code = ? ";
        }
        sql += "group by ps.capacity, ps.work_core_name, ps.work_class_name, ps.work_time_start, ps.work_time_end, ps.work_time";

        List<Map> list = super.sqlQueryByNativeSqlToMap(sql.toString(), paramsList.toArray());
        return list;
    }
}
