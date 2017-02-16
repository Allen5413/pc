package com.allen.service.basic.produceline.impl;

import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.service.basic.produceline.FindProduceLineByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindProduceLineByIdServiceImpl implements FindProduceLineByIdService {

    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    public ProduceLine find(long id) throws Exception {
        return produceLineDao.findOne(id);
    }
}
