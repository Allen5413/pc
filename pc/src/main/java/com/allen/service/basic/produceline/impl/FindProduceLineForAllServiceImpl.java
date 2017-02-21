package com.allen.service.basic.produceline.impl;

import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.service.basic.produceline.FindProduceLineForAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/2/21 0021.
 */
@Service
public class FindProduceLineForAllServiceImpl implements FindProduceLineForAllService {

    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    public List<ProduceLine> find() throws Exception {
        return (List<ProduceLine>) produceLineDao.findAll();
    }
}
