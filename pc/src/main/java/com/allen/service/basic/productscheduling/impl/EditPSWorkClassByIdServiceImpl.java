package com.allen.service.basic.productscheduling.impl;

import com.allen.dao.basic.productscheduling.ProductSchedulingDao;
import com.allen.entity.basic.ProductScheduling;
import com.allen.service.basic.productscheduling.EditPSWorkClassByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/6/16 0016.
 */
@Service
public class EditPSWorkClassByIdServiceImpl implements EditPSWorkClassByIdService {

    @Resource
    private ProductSchedulingDao productSchedulingDao;

    @Override
    public void edit(long id, String code, String name) throws Exception {
        ProductScheduling productScheduling = productSchedulingDao.findOne(id);
        if(null != productScheduling){
            productScheduling.setWorkClassCode(code);
            productScheduling.setWorkClassName(name);
            productSchedulingDao.save(productScheduling);
        }
    }
}
