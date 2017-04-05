package com.allen.service.basic.planordera.impl;

import com.allen.dao.basic.planordera.PlanOrderADao;
import com.allen.dao.basic.zplanordera.ZPlnPlanOrderADao;
import com.allen.entity.basic.PlanOrderA;
import com.allen.entity.basic.ZPlanOrderA;
import com.allen.service.basic.planordera.AddPlanOrderAService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Allen on 2017/4/5 0005.
 */
@Service
public class AddPlanOrderAServiceImpl implements AddPlanOrderAService {

    @Resource
    private PlanOrderADao planOrderADao;
    @Resource
    private ZPlnPlanOrderADao zPlnPlanOrderADao;

    @Override
    @Transactional
    public void add(long fId, BigDecimal FBASEYIELDQTY) throws Exception {
        ZPlanOrderA zPlanOrderA = new ZPlanOrderA();
        zPlanOrderA.setColumn1(1);
        zPlanOrderA = zPlnPlanOrderADao.save(zPlanOrderA);

        PlanOrderA planOrderA = new PlanOrderA();
        planOrderA.setFENTRYID(zPlanOrderA.getId());
        planOrderA.setFID(fId);
        planOrderA.setFISSKIP('0');
        planOrderA.setFYIELDRATE(new BigDecimal(100));
        planOrderA.setFBASEYIELDQTY(FBASEYIELDQTY);
        planOrderA.setFExtendControl(0);
        planOrderADao.save(planOrderA);

        zPlnPlanOrderADao.delete(zPlanOrderA);
    }
}
