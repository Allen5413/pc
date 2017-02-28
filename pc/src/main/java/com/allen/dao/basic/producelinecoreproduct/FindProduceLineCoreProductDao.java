package com.allen.dao.basic.producelinecoreproduct;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.ProduceLineCoreProduct;
import org.springframework.stereotype.Service;

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
}
