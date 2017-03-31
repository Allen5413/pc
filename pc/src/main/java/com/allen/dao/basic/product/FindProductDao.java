package com.allen.dao.basic.product;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductType;
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
        if(paramsMap==null){
            paramsMap = new HashMap<String, Object>();
        }
        paramsMap.put("a.FDOCUMENTSTATUS","C");
        paramsMap.put("a.FFORBIDSTATUS","A");
        String fields = "b.FENTRYID,a.FMATERIALID,a.FNUMBER,d.FNAME as cateGoryName,b.FERPCLSID,c.FNAME,b.FSNO";
        String[] tableNames = {"t_bd_material a,t_bd_materialbase b,t_bd_material_l c,t_bd_materialcategory_l d"};
        String defaultWhere = "a.FMATERIALID = b.FMATERIALID and  c.FMATERIALID = a.FMATERIALID and b.FCATEGORYID = d.FCATEGORYID and a.FUSEORGID=100001 and d.FCATEGORYID in (239,241) ";
        return super.findPageByNativeSqlToMap(pageInfo, fields,defaultWhere,tableNames, paramsMap, sortMap);
    }

    public  List<Map> findByMap(Map<String,Object> paramsMap, Map<String, Boolean> sortMap){
        if(paramsMap==null){
            paramsMap = new HashMap<String, Object>();
        }
        paramsMap.put("a.FDOCUMENTSTATUS","C");
        paramsMap.put("a.FFORBIDSTATUS","A");
        String fields = "b.FENTRYID,a.FNUMBER,d.FNAME as cateGoryName,b.FERPCLSID,c.FNAME,b.FCATEGORYID,b.FMATERIALID";
        String[] tableNames = {"t_bd_material a,t_bd_materialbase b,t_bd_material_l c,t_bd_materialcategory_l d"};
        String defaultWhere = "a.FMATERIALID = b.FMATERIALID and  c.FMATERIALID = a.FMATERIALID and b.FCATEGORYID = d.FCATEGORYID and and a.FUSEORGID=100001";
        return super.findListBySqlToMap(tableNames,fields,defaultWhere,paramsMap,sortMap);
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
        return super.findListByHql(tableNames,fields,paramsMap,null,Product.class);
    }

    /**
     * 功能:查询生产线关联中心下的产品
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public List<Map> findByPlIdAndWcId(Map<String,Object> paramsMap) throws Exception{
        String fields = "p.FMATERIALID, p.FNUMBER, pn.FNAME, pt.FNAME cateGoryName, pbase.FERPCLSID, plcp.id plcpId, plcp.qualified_rate qualifiedRate";
        String[] tableNames = {"produce_line_core_product plcp, produce_line_core plc,t_bd_material p," +
                "t_bd_materialbase pbase,t_bd_materialcategory_l pt,t_bd_material_l pn"};
        String defaultWhere = "plcp.produce_line_core_id = plc.id and p.FMATERIALID = plcp.product_id " +
                "and pbase.FMATERIALID = p.FMATERIALID and pbase.FCATEGORYID = pt.FCATEGORYID and " +
                "pn.FMATERIALID = p.FMATERIALID";
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.FNUMBER",false);
        return super.findListBySqlToMap(tableNames, fields, defaultWhere, paramsMap, sortMap);
    }

    /**
     * 功能:查询生产线关联的产品信息
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public List<Map> findByPlId(Map<String,Object> paramsMap) throws Exception{
        String fields = "DISTINCT p.FMATERIALID,p.FNUMBER,pn.FNAME";
        String[] tableNames = {"produce_line_core_product plcp, produce_line_core plc,t_bd_material p,t_bd_material_l pn"};
        String defaultWhere = "plcp.produce_line_core_id = plc.id and p.FMATERIALID =  plcp.product_id and p.FMATERIALID = pn.FMATERIALID";
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.FNUMBER",false);
        return super.findListBySqlToMap(tableNames, fields, defaultWhere, paramsMap, sortMap);
    }
}
