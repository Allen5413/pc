package com.allen.dao.basic.productscheduling;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProductScheduling;
import com.allen.util.DateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 包路径：com.allen.dao.basic.productionplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-20 20:31
 */
@Service
public class FindProductSchedulingDao extends BaseQueryDao {

    /**
     * 功能:查询未使用的排班信息
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List<ProductScheduling> find(Date start,Date end) throws Exception{
        String sql = "select a.id from product_scheduling a left join TaskInfo b on a.id = b.schedulingid " +
                "where a.production_date>=? and a.production_date<=? and b.schedulingid is NULL ";
        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter(0,start);
        sqlQuery.setParameter(1,end);
        List<Map> results = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<ProductScheduling> schedulingArrayList = new ArrayList<ProductScheduling>();
        ProductScheduling scheduling = null;
        if(results!=null&&results.size()>0){
            for(Map map:results){
                scheduling = new ProductScheduling();
                scheduling.setId(Long.parseLong(map.get("id").toString()));
                schedulingArrayList.add(scheduling);
            }
        }
        return schedulingArrayList;
    }
}
