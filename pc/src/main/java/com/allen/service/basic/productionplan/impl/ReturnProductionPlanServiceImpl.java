package com.allen.service.basic.productionplan.impl;

import com.allen.dao.basic.planorder.FindPlanOrderDao;
import com.allen.dao.basic.planorder.PlanOrderDao;
import com.allen.dao.basic.productionplan.ProductionPlanDao;
import com.allen.dao.basic.zplanorder.ZPlnPlanOrderDao;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.ProductionPlan;
import com.allen.entity.basic.ZPlanOrder;
import com.allen.service.basic.productionplan.ReturnProductionPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 2017/3/30 0030.
 */
@Service
public class ReturnProductionPlanServiceImpl implements ReturnProductionPlanService {

    @Resource
    private ProductionPlanDao productionPlanDao;
    @Resource
    private FindPlanOrderDao findPlanOrderDao;
    @Resource
    private PlanOrderDao planOrderDao;
    @Resource
    private ZPlnPlanOrderDao zPlnPlanOrderDao;
    @Transactional
    @Override
    public void returnPlan(Date startDate, Date endDate) throws Exception {
        List<ProductionPlan> productionPlanList = productionPlanDao.findByProductionDateBetween(startDate, endDate);
        if(null != productionPlanList && 0 < productionPlanList.size()){
            List<PlanOrder> planOrders = null;
            BigDecimal ZERO = new BigDecimal(0);
            PlanOrder planOrder = null;
            ZPlanOrder zPlanOrder = null;
            for (ProductionPlan productionPlan : productionPlanList){
                planOrders  = findPlanOrderDao.findPlanOrderByMaterIdAndDemandDate(productionPlan.getProductId(),
                        productionPlan.getProductionDate());
                if(productionPlan.getActualProductionNum().compareTo(ZERO)<=0){
                    continue;
                }
                if(planOrders!=null&&planOrders.size()>0){
                    planOrder = planOrders.get(0);
                    planOrder.setFFIRMQTY(productionPlan.getActualProductionNum());
                }else{
                    zPlanOrder = new ZPlanOrder();
                    zPlanOrder.setColumn1(1);
                    zPlanOrder = zPlnPlanOrderDao.save(zPlanOrder);
                    planOrder = new PlanOrder();
                    planOrder.setFID(zPlanOrder.getId());
                    planOrder.setFFIRMQTY(productionPlan.getActualProductionNum());
                    planOrder.setFDEMANDDATE(productionPlan.getProductionDate());
                    planOrder.setFMATERIALID(productionPlan.getProductId());
                    planOrder.setFDOCUMENTSTATUS("A");
                    planOrder.setFRELEASETYPE("1");
                    planOrder.setFDATASOURCE("1");
                    planOrder.setFBASEDEMANDQTY(productionPlan.getActualProductionNum());
                    planOrder.setFBASEORDERQTY(productionPlan.getActualProductionNum());
                    planOrder.setFBASESUGQTY(productionPlan.getActualProductionNum());
                    planOrder.setFDEMANDQTY(productionPlan.getActualProductionNum());
                    planOrder.setFORDERQTY(productionPlan.getActualProductionNum());
                    planOrder.setFBASEFIRMQTY(productionPlan.getActualProductionNum());
                    planOrder.setFSUGQTY(productionPlan.getActualProductionNum());
                    zPlnPlanOrderDao.delete(zPlanOrder);
                }
                planOrderDao.save(planOrder);
            }
        }
    }
}
