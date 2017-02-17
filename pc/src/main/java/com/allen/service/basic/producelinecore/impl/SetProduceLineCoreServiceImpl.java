package com.allen.service.basic.producelinecore.impl;

import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.service.basic.producelinecore.SetProduceLineCoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/17 0017.
 */
@Service
public class SetProduceLineCoreServiceImpl implements SetProduceLineCoreService {

    @Resource
    private ProduceLineCoreDao produceLineCoreDao;

    @Override
    @Transactional
    public void setForCore(long wcId, Long[] plIds, String loginName) throws Exception {
        //先删除之前的关联
        produceLineCoreDao.delByWorkCoreId(wcId);
        //添加新的关联
        if(null != plIds && 0 < plIds.length){
            for (Long plId : plIds){
                ProduceLineCore produceLineCore = new ProduceLineCore();
                produceLineCore.setWorkCoreId(wcId);
                produceLineCore.setProduceLineId(plId);
                produceLineCore.setOperator(loginName);
                produceLineCoreDao.save(produceLineCore);
            }
        }
    }

    @Override
    @Transactional
    public void setForLine(long plId, Long[] wcIds, String loginName) throws Exception {
        //先删除之前的关联
        produceLineCoreDao.delByProduceLineId(plId);
        //添加新的关联
        if(null != wcIds && 0 < wcIds.length){
            for (Long wcId : wcIds){
                ProduceLineCore produceLineCore = new ProduceLineCore();
                produceLineCore.setWorkCoreId(wcId);
                produceLineCore.setProduceLineId(plId);
                produceLineCore.setOperator(loginName);
                produceLineCoreDao.save(produceLineCore);
            }
        }
    }
}
