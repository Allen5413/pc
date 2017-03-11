package com.allen.service.basic.produceline.impl;

import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.service.basic.produceline.DelProduceLineService;
import com.allen.service.basic.producelinecore.DelProduceLineCoreByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelProduceLineServiceImpl implements DelProduceLineService {

    @Resource
    private ProduceLineDao produceLineDao;
    @Resource
    private ProduceLineCoreDao produceLineCoreDao;
    @Resource
    private DelProduceLineCoreByIdService delProduceLineCoreByIdService;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        //查询生产线关联的工作中心
        List<ProduceLineCore> produceLineCoreList = produceLineCoreDao.findByProduceLineId(id);
        if(null != produceLineCoreList && 0 < produceLineCoreList.size()){
            for(ProduceLineCore produceLineCore : produceLineCoreList){
                delProduceLineCoreByIdService.del(produceLineCore.getId());
            }
        }
        produceLineDao.delete(id);
    }
}
