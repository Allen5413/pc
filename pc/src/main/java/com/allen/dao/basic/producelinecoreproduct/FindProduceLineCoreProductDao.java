package com.allen.dao.basic.producelinecoreproduct;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.ProduceLineCoreProduct;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/28.
 */
@Service
public class FindProduceLineCoreProductDao extends BaseQueryDao {

    /**
     * 通过生产线id和产品id，查询下面所有的关联
     * @param plId
     * @return
     * @throws Exception
     */
    public List<ProduceLineCoreProduct> findByPlIdAndPId(long plId, long pId)throws Exception{
        String fields = "plcp";
        String[] tableNames = {"ProduceLineCoreProduct plcp", "ProduceLineCore plc"};
        String defaultWhere = "plcp.produceLineCoreId = plc.id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("plc.produceLineId", plId);
        params.put("plcp.productId", pId);
        return super.findListByHql(tableNames, fields, defaultWhere, params, null, ProduceLineCoreProduct.class);
    }

    /**
     * 功能：根据生产先id和产品id获取产品需要经过的工作中心
     * @param plId
     * @param productId
     * @return
     * @throws Exception
     */
    public List<Map> findPlcpByLineIdAndProductId(long plId,long productId){
        String fields="b.id,b.qualified_rate,a.produce_line_id,a.work_core_id";
        String[] tableNames = {"produce_line_core a", "produce_line_core_product b"};
        String defaultWhere="a.id = b.produce_line_core_id";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("a.produce_line_id",plId);//生产线id
        params.put("b.product_id",productId);//产品id
        return super.findListBySqlToMap(tableNames,fields,defaultWhere,params,null);
    }

    /**
     * 功能：根据生产中心产品ID,获取工作组模式信息,根据工作组和模式排序
     * @param plcpId
     * @return
     */
    public List<Map> findPlcpgByPlcpId(long plcpId){
        String fields = "c.begin_time,c.end_time,c.sno as workTimeSno,a.sno,a.min_batch,a.class_group_id,a.unit_time_capacity,c.id as workTimeId";
        String[] tableNames = {"produce_line_core_product_cg a", "work_mode_time b","work_time c"};
        String defaultWhere = "a.work_mode_id = b.work_mode_id and b.work_time_id = c.id";
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("a.produce_line_core_product_id",plcpId);
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("a.sno",true);
        sortMap.put("c.sno",true);
        return super.findListBySqlToMap(tableNames,fields,defaultWhere,params,sortMap);
    }

}
