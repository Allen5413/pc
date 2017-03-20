package com.allen.dao.basic.producelineuse;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.ProduceLineUse;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.allen.dao.basic.producelineuse
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-07 20:54
 */
@Service
public class FindProduceLineUseDao extends BaseQueryDao {

    /**
     * 功能：根据产品id 生产日期
     * @param productId 产品id
     * @return
     */
    public List<Map> findUnUserProduceLine(long productId){
        String sql = "select f.begin_time,f.end_time,c.qualified_rate,b.produce_line_id,b.work_core_id,d.class_group_id, " +
                "d.unit_time_capacity,d.sno,c.product_id,d.min_batch,f.sno as workTimeSno,f.id as work_time_id,a.is_public,b.sno as workCoreSno " +
                "from produce_line a,produce_line_core b,produce_line_core_product c ,produce_line_core_product_cg d,work_time f  " +
                "where  a.id = b.produce_line_id and b.id = c.produce_line_core_id " +
                "and c.product_id = ? and c.id = d.produce_line_core_product_id  " +
                "and d.work_mode_id = f.id order by b.sno,d.sno,f.sno";
        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter(0,productId);
        List<Map> results = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return results;
    }

    /**
     * 功能：根据生产计划日期、生产线id  生产中心id 查找到已经使用情况
     * @param productionDate 生产日期
     * @param produceLineId 生产线id
     * @param workCoreId 工作中心id
     * @return
     */
    public List<ProduceLineUse> findProduceLineUse(Date productionDate, long produceLineId, long workCoreId,int flag){
        String fields = "p";
        String[] tableNames = {"ProduceLineUse p"};
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("p.productionDate",productionDate);
        params.put("p.produceLineId",produceLineId);
        params.put("p.workCoreId",workCoreId);
        return super.findListByHql(tableNames,fields,params,null,ProduceLineUse.class);
    }

    /**
     * 功能：根据条件获取生产线使用情况
     * @param params
     * @return
     */
    public List<ProduceLineUse> findProduceLineUse(Map<String,Object> params){
        String fields = "p";
        String[] tableNames = {"ProduceLineUse p"};
        return super.findListByHql(tableNames,fields,params,null,ProduceLineUse.class);
    }
}
