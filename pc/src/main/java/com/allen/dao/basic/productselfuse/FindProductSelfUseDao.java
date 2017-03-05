package com.allen.dao.basic.productselfuse;

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
public class FindProductSelfUseDao extends BaseQueryDao {

    public  List<Map> findByMap(Map<String,Object> paramsMap, Map<String, Boolean> sortMap){
        if(paramsMap==null){
            paramsMap = new HashMap<String, Object>();
        }
        if(sortMap == null){
            sortMap = new HashMap<String, Boolean>();
            sortMap.put("FSEQ",true);
        }
        paramsMap.put("a.FDOCUMENTSTATUS","C");
        paramsMap.put("a.FFORBIDSTATUS","A");
        paramsMap.put("d.FDOCUMENTSTATUS","C");
        paramsMap.put("d.FFORBIDSTATUS","A");
        String fields = "a.FNUMBER,b.FERPCLSID,c.FNAME,e.FNUMERATOR/e.FDENOMINATOR useQty,e.FOFFSETTIME,e.FSEQ,d.FID,e.FMATERIALID";
        String[] tableNames = {"t_eng_bom d,t_eng_bomchild e,t_bd_material a,t_bd_materialbase b,t_bd_material_l c"};
        String defaultWhere = "d.FID = e.FID and e.FMATERIALID = b.FMATERIALID and  a.FMATERIALID = b.FMATERIALID and  c.FMATERIALID = a.FMATERIALID ";
        return super.findListBySqlToMap(tableNames,fields,defaultWhere,paramsMap,sortMap);
    }
}