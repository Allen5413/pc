package com.allen.service.basic.produceline.impl;

import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.service.basic.produceline.DelProduceLineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelProduceLineServiceImpl implements DelProduceLineService {

    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        produceLineDao.delete(id);
    }
}
