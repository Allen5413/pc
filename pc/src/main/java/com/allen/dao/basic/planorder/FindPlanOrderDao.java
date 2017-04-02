package com.allen.dao.basic.planorder;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.Product;
import com.allen.util.DateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Allen on 2017/2/14 0014.
 */
@Service
public class FindPlanOrderDao extends BaseQueryDao {

    /**
     * 功能：获取订单生产计划
     * @return
     */
    public List<PlanOrder> findPlanOrder(Date start, Date end){
        String sql = "select a.FNUMBER,b.FNAME,po.FBILLNO,po.FDOCUMENTSTATUS,po.FFIRMQTY,po.FRELEASETYPE,po.FMATERIALID," +
                "po.FDEMANDDATE,c.FCUSTID,f.FCATEGORYID " +
                "from T_BD_MATERIALBASE f,T_BD_MATERIAL a,T_BD_MATERIAL_L b,T_PLN_PLANORDER po left join T_PLN_PLANORDER_B pob on po.FID=pob.FID " +
                "left join T_SAL_ORDER so on pob.FSALEORDERID=so.FID left join T_BD_CUSTOMER c on so.FCUSTID=c.FCUSTID " +
                "where f.FMATERIALID = a.FMATERIALID AND a.FMATERIALID = b.FMATERIALID and b.FMATERIALID = po.FMATERIALID " +
                "and po.FDOCUMENTSTATUS='A' and po.FRELEASETYPE=1 AND po.FDEMANDDATE>=? and po.FDEMANDDATE<=?  ORDER BY f.FSNO,po.FMATERIALID,po.FDEMANDDATE";

        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter(0,start);
        sqlQuery.setParameter(1,end);
        List<Map> results = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<PlanOrder> planOrders = new ArrayList<PlanOrder>();
        PlanOrder planOrder = null;
        if(results!=null&&results.size()>0){
            for(Map map:results){
                planOrder = new PlanOrder();
                planOrder.setFBILLNO(map.get("FBILLNO")==null?"":map.get("FBILLNO").toString());
                planOrder.setFDOCUMENTSTATUS(map.get("FDOCUMENTSTATUS").toString());
                planOrder.setFFIRMQTY(new BigDecimal(map.get("FFIRMQTY").toString()));
                planOrder.setFRELEASETYPE(map.get("FRELEASETYPE").toString());
                planOrder.setFMATERIALID(Long.parseLong(map.get("FMATERIALID").toString()));
                planOrder.setFDEMANDDATE(DateUtil.getFormatDate(map.get("FDEMANDDATE").toString(),DateUtil.shortDatePattern));
                planOrder.setFCUSTID(map.get("FCUSTID")==null?0:Long.parseLong(map.get("FCUSTID").toString()));
                planOrder.setFNUMBER(map.get("FNUMBER").toString());
                planOrder.setFNAME(map.get("FNAME").toString());
                planOrder.setFCATEGORYID(map.get("FCATEGORYID").toString());
                planOrders.add(planOrder);
            }
        }
        return planOrders;
    }

    /**
     * 查询生产计划
     * @param fMaterialId
     * @param demandDate
     * @return
     */
    public List<PlanOrder> findPlanOrderByMaterIdAndDemandDate(long fMaterialId, Date demandDate){
        String fields = "p";
        String[] tableNames = {"PlanOrder p"};
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("p.FMATERIALID",fMaterialId);
        params.put("p.FDEMANDDATE",demandDate);
        params.put("p.FRELEASETYPE","1");
        params.put("p.FDOCUMENTSTATUS","A");
        return super.findListByHql(tableNames,fields,params,null,PlanOrder.class);
    }
}
