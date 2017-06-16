package com.allen.service.basic.productscheduling.impl;

import com.allen.dao.basic.productscheduling.ProductSchedulingDao;
import com.allen.entity.basic.ProductScheduling;
import com.allen.service.basic.productscheduling.EditPSCapacityByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Allen on 2017/6/16 0016.
 */
@Service
public class EditPSCapacityByIdServiceImpl implements EditPSCapacityByIdService {

    @Resource
    private ProductSchedulingDao productSchedulingDao;

    @Override
    public void edit(long id, BigDecimal count) throws Exception {
        ProductScheduling productScheduling = productSchedulingDao.findOne(id);
        if(null != productScheduling){
            productScheduling.setCapacity(count);
            productSchedulingDao.save(productScheduling);
        }
    }
}
