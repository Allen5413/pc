package com.allen.dao.basic.producelinecoreproductcg;

import com.allen.dao.BaseQueryDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/9.
 */
@Service
public class FindProduceLineCoreProductCgDao extends BaseQueryDao {

    /**
     * 查询一个 生产线-工作中心-产品 的关联班组信息
     * @param plcpId
     * @return
     * @throws Exception
     */
    public List<Map> findByPlcpId(long plcpId)throws Exception{
        String fields = "p.*, wm.name wmName, cg.name cgName, ROUND(p.unit_time_capacity / 3600, 2) hour";
        String[] tableNames = {"produce_line_core_product_cg p, work_time wm, class_group cg"};
        String defaultWhere = "p.work_mode_id = wm.id and p.class_group_id = cg.id";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("p.produce_line_core_product_id", plcpId);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.sno", true);
        return super.findListBySqlToMap(tableNames, fields, defaultWhere, param, sortMap);
    }
}
