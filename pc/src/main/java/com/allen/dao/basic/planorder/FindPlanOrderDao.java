package com.allen.dao.basic.planorder;

import com.allen.dao.BaseQueryDao;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.Product;
import com.allen.util.DateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/14 0014.
 */
@Service
public class FindPlanOrderDao extends BaseQueryDao {

    /**
     * 功能：获取订单生产计划
     * @return
     */
    public List<PlanOrder> findPlanOrder(){
        String sql = "select po.FBILLNO,po.FDOCUMENTSTATUS,po.FFIRMQTY,po.FRELEASETYPE,po.FMATERIALID,po.FDEMANDDATE,c.FCUSTID from T_PLN_PLANORDER po " +
                "left join T_PLN_PLANORDER_B pob on po.FID=pob.FID left join T_SAL_ORDER so on pob.FSALEORDERID=so.FID left join " +
                "T_BD_CUSTOMER c on so.FCUSTID=c.FCUSTID where po.FDOCUMENTSTATUS='A' and po.FRELEASETYPE=1 ORDER BY po.FMATERIALID,po.FDEMANDDATE";

        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        List<Map> results = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<PlanOrder> planOrders = new ArrayList<PlanOrder>();
        PlanOrder planOrder = null;
        if(results!=null&&results.size()>0){
            for(Map map:results){
                planOrder = new PlanOrder();
                planOrder.setFBILLNO(map.get("FBILLNO").toString());
                planOrder.setFDOCUMENTSTATUS(map.get("FDOCUMENTSTATUS").toString());
                planOrder.setFFIRMQTY(new BigDecimal(map.get("FFIRMQTY").toString()));
                planOrder.setFRELEASETYPE(map.get("FRELEASETYPE").toString());
                planOrder.setFMATERIALID(Long.parseLong(map.get("FMATERIALID").toString()));
                planOrder.setFDEMANDDATE(DateUtil.getFormatDate(map.get("FDEMANDDATE").toString(),DateUtil.shortDatePattern));
                planOrder.setFCUSTID(map.get("FCUSTID")==null?0:Long.parseLong(map.get("FCUSTID").toString()));
                planOrders.add(planOrder);
            }
        }
        return planOrders;
    }
}
