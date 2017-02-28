package com.allen.dao.basic.product;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 功能:查询下拉列表中的产品信息
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public List<Product> find(Map<String,Object> paramsMap) throws Exception{
        String fields = "p";
        String[] tableNames = {"Product p"};
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.code",false);
        return super.findListByHql(tableNames,fields,paramsMap,sortMap,Product.class);
    }

    /**
     * 功能:查询生产线关联中心下的产品
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public List<Map> findByPlIdAndWcId(Map<String,Object> paramsMap) throws Exception{
        String fields = "p.id, p.code, p.name, pt.name tName, p.self_made selfMade, plcp.work_mode_id wmId, plcp.id plcpId";
        String[] tableNames = {"product p, produce_line_core_product plcp, produce_line_core plc, product_type pt"};
        String defaultWhere = "p.type = pt.id and p.id = plcp.product_id and plcp.produce_line_core_id = plc.id";
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.code",false);
        return super.findListBySqlToMap(tableNames, fields, defaultWhere, paramsMap, sortMap);
    }
}
