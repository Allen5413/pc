package com.allen.dao.basic.productionplan;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductionPlan;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
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
    @Transactional
    public List<ProductionPlan> find(Map<String,Object> params){
        String sql = "select p from ProductionPlan p where 1=1 ";
        List args = new ArrayList();
        if(params!=null){
            if(params.get("p.productionDateStart")!=null){
                sql += " AND p.productionDate>=?";
                args.add(params.get("p.productionDateStart"));
            }
            if(params.get("p.productionDateEnd")!=null){
                sql += " AND p.productionDate <=?";
                args.add(params.get("p.productionDateEnd"));
            }
            if(params.get("p.productName")!=null&&!StringUtil.isEmpty(params.get("p.productName").toString())){
                sql += " AND p.productName like ?";
                args.add(params.get("p.productName"));
            }
            if(params.get("p.productType")!=null&&!StringUtil.isEmpty(params.get("p.productType").toString())){
                sql += " AND p.productType = ?";
                args.add(params.get("p.productType"));
            }
        }
        sql += " ORDER BY p.productId desc";
        Query query = this.entityManager.createQuery(sql, ProductionPlan.class);
        if(args != null && args.size() != 0) {
            for(int i = 0; i < args.size(); ++i) {
                query.setParameter((i+1), args.get(i));
            }
        }
        return query.getResultList();
    }

}
