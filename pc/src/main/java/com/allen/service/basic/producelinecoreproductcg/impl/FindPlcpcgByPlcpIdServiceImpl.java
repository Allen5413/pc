package com.allen.service.basic.producelinecoreproductcg.impl;

import com.allen.dao.basic.producelinecoreproductcg.FindProduceLineCoreProductCgDao;
import com.allen.service.basic.producelinecoreproductcg.FindPlcpcgByPlcpIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/9.
 */
@Service
public class FindPlcpcgByPlcpIdServiceImpl implements FindPlcpcgByPlcpIdService {

    @Resource
    private FindProduceLineCoreProductCgDao findProduceLineCoreProductCgDao;

    @Override
    public List<Map> find(long plcpId) throws Exception {
        return findProduceLineCoreProductCgDao.findByPlcpId(plcpId);
    }
}
