package com.allen.service.basic.productscheduling.impl;

import com.allen.dao.basic.productscheduling.ProductSchedulingDao;
import com.allen.entity.basic.ProductScheduling;
import com.allen.service.basic.productscheduling.FindPSByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/6/16 0016.
 */
@Service
public class FindPSByIdServiceImpl implements FindPSByIdService {

    @Resource
    private ProductSchedulingDao productSchedulingDao;

    @Override
    public ProductScheduling findById(long id) throws Exception {
        return productSchedulingDao.findOne(id);
    }
}
