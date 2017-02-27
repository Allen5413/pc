package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.service.basic.producelinecoreproduct.DelProduceLineCoreProductByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class DelProduceLineCoreProductByIdServiceImpl implements DelProduceLineCoreProductByIdService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;

    @Override
    public void del(long id) throws Exception {
        produceLineCoreProductDao.delete(id);
    }
}
