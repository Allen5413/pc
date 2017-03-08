package com.allen.dao.basic.producelineuse;

import com.allen.dao.BaseQueryDao;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.Date;
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
     * 功能：根据产品id获取未加工产品的非公共生产线 生产日期
     * @param productId 产品id
     * @param productionDate 生产日期
     * @param isPublic 是否公共线 0 否 1 是
     * @return
     */
    public List<Map> findUnUserProduceLine(long productId,Date productionDate,int isPublic){
        String sql = "select * from produce_line a left join produce_line_use b on a.id = b.produce_line_id " +
            " and b.production_date = ? and b.flag=1 " +/*生产日期为计划时间  flag =1 表示剩余生产线时间的*/
            " where a.product_ids like ? and a.is_public = ? order by b.c_id";
        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter(0,productionDate);
        sqlQuery.setParameter(1,"%"+productId+"%");
        sqlQuery.setParameter(2,isPublic);
        List<Map> results = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return results;
    }
}
