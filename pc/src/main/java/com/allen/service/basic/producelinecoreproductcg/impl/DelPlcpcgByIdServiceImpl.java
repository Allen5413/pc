package com.allen.service.basic.producelinecoreproductcg.impl;

import com.allen.dao.basic.producelinecoreproductcg.ProduceLineCoreProductCgDao;
import com.allen.service.basic.producelinecoreproductcg.DelPlcpcgByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class DelPlcpcgByIdServiceImpl implements DelPlcpcgByIdService {

    @Resource
    private ProduceLineCoreProductCgDao produceLineCoreProductCgDao;

    @Override
    public void del(long id) throws Exception {
        produceLineCoreProductCgDao.delete(id);
    }
}
