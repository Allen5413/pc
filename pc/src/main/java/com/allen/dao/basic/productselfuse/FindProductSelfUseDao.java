package com.allen.dao.basic.productselfuse;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    /**
     * 功能：根据产品id查找产品组成明细
     * @param products 产品集合
     * @param productId 上级产品id
     * @param parentProductNum 上级残片用量
     * @param level 当前产品所在层级
     * @return
     */
    public void findProductChild(List<Map> products, long productId, BigDecimal parentProductNum, int level){
        Map<String,Object>  paramsMap = new HashMap<String, Object>();
        paramsMap.put("d.FDOCUMENTSTATUS","C");
        paramsMap.put("d.FFORBIDSTATUS","A");
        paramsMap.put("d.FMATERIALID",Long.valueOf(productId));
        String fields = "e.FNUMERATOR/e.FDENOMINATOR*"+parentProductNum.doubleValue()
                +" useQty,e.FOFFSETTIME,e.FSEQ,d.FID,e.FMATERIALID,a.FCATEGORYID,b.FNUMBER,c.FNAME";
        String[] tableNames = {"t_eng_bom d,t_eng_bomchild e,t_bd_materialbase a,t_bd_material b,t_bd_material_l c"};
        String defaultWhere = "d.FID = e.FID and e.FMATERIALID = a.FMATERIALID and a.FMATERIALID = b.FMATERIALID and b.FMATERIALID = c.FMATERIALID ";
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("e.FSEQ",true);
        List<Map> childProducts =  super.findListBySqlToMap(tableNames,fields,defaultWhere,paramsMap,sortMap);
        if(childProducts==null||childProducts.size()==0){
            return ;
        }
        level++;
        for(Map childProduct:childProducts) {
            if (products.get(products.size() - 1).get("childs") == null) {
                if("239".equals(childProduct.get("FCATEGORYID").toString())||
                        "241".equals(childProduct.get("FCATEGORYID").toString())){
                    List<String> childs = new ArrayList<String>();
                    childs.add(childProduct.get("FMATERIALID").toString()+","+childProduct.get("useQty").toString()
                            +","+childProduct.get("FCATEGORYID").toString());
                    products.get(products.size() - 1).put("childs", childs);
                }
            } else {
                if ("239".equals(childProduct.get("FCATEGORYID").toString()) ||
                        "241".equals(childProduct.get("FCATEGORYID").toString())) {
                    List<String> childs = (List<String>) products.get(products.size() - 1).get("childs");
                    childs.add(childProduct.get("FMATERIALID").toString() + "," + childProduct.get("useQty").toString()
                            + "," + childProduct.get("FCATEGORYID").toString());
                    products.get(products.size() - 1).put("childs", childs);
                }
            }
        }
        for(Map childProduct:childProducts){
            childProduct.put("level",level);
            products.add(childProduct);
            findProductChild(products,Long.parseLong(childProduct.get("FMATERIALID").toString()),
                    new BigDecimal(childProduct.get("useQty").toString()),level);
        }
    }
}
