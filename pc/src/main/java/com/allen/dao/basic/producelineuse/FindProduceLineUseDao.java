package com.allen.dao.basic.producelineuse;

import com.allen.dao.BaseQueryDao;
import com.allen.entity.basic.ProduceLineUse;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<Map> findUnUserProduceLine(long productId){
        String sql = "select f.begin_time,f.end_time,c.qualified_rate,b.produce_line_id,b.work_core_id,d.class_group_id,a.extend, " +
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

    /**
     * 功能：查询排班信信息总和详细信息
     * @param start
     * @param end
     * @return
     */
    public List<Map<String,Object>> findProduceLineUseDetail(Date start,Date end){
        String sql = "select bname.FNAME,bnum.FNUMBER,a.plan_quantity,a.capacity,a.production_date,wcore.name as wName,cg.name as cgName," +
                "wt.name as wtName,wt.sno,a.work_start,a.work_time,cg.code as cgCode,wcore.code as wCode,wt.code as wtCode " +
                "from produce_line_use a,t_bd_material bnum,t_bd_material_l bname,work_core wcore,class_group cg,work_time wt " +
                "where a.product_id = bnum.FMATERIALID and a.product_id = bname.FMATERIALID and a.work_core_id = wcore.id " +
                "and cg.id = a.work_team_id and a.work_time_id = wt.id and a.production_date>=? and a.production_date<=? " +
                " and work_time>0 order by a.product_id,a.production_date,a.work_team_id";
        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter(0,start);
        sqlQuery.setParameter(1,end);
        return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }
}
