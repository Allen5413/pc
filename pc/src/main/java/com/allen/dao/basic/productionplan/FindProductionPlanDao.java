package com.allen.dao.basic.productionplan;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductionPlan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.allen.dao.basic.productionplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:31
 */
@Service
public class FindProductionPlanDao extends BaseQueryDao {
    public List<ProductionPlan> find(Map<String,Object> params){
        String fields = "p";
        String[] tableNames = {"ProductionPlan p"};
        Map<String,Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("productId",true);
        return super.findListByHql(tableNames,fields,params,sortMap,ProductionPlan.class);
    }

}
