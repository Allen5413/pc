package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.FindProduceLineCoreProductByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/28.
 */
@Service
public class FindProduceLineCoreProductByIdServiceImpl implements FindProduceLineCoreProductByIdService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;

    @Override
    public ProduceLineCoreProduct find(long id) throws Exception {
        return produceLineCoreProductDao.findOne(id);
    }
}
