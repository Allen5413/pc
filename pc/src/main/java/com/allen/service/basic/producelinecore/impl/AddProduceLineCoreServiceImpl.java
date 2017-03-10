package com.allen.service.basic.producelinecore.impl;

import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.service.basic.producelinecore.AddProduceLineCoreService;
import com.allen.service.basic.producelinecore.DelProduceLineCoreByIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class AddProduceLineCoreServiceImpl implements AddProduceLineCoreService {

    @Resource
    private ProduceLineCoreDao produceLineCoreDao;
    @Resource
    private DelProduceLineCoreByIdService delProduceLineCoreByIdService;

    @Override
    @Transactional
    public void add(long plId, String delPlcIds, Long[] plcIds, Long[] wcIds, Integer[] snos, String loginName) throws Exception {
        //删掉的工作中心
        if(!StringUtil.isEmpty(delPlcIds)){
            for(String delPlcId : delPlcIds.split(",")){
                delProduceLineCoreByIdService.del(Long.parseLong(delPlcId));
            }
        }
        if(null != plcIds && 0 < plcIds.length) {
            for(int i=0; i < plcIds.length; i++) {
                long plcId = plcIds[i];
                long wcId = wcIds[i];
                int sno = snos[i];

                //==0说明是新增的中心信息，否则就是之前关联的中心
                if(0 == plcId){
                    ProduceLineCore produceLineCore = new ProduceLineCore();
                    produceLineCore.setProduceLineId(plId);
                    produceLineCore.setWorkCoreId(wcId);
                    produceLineCore.setSno(sno);
                    produceLineCore.setOperator(loginName);
                    produceLineCoreDao.save(produceLineCore);
                }else {
                    ProduceLineCore produceLineCore = produceLineCoreDao.findOne(plcId);
                    //如果中心没变，只修改序号；否则删除中心信息重新关联
                    if(produceLineCore.getWorkCoreId() != wcId){
                        delProduceLineCoreByIdService.del(produceLineCore.getId());
                        produceLineCore = new ProduceLineCore();
                        produceLineCore.setProduceLineId(plId);
                        produceLineCore.setWorkCoreId(wcId);
                    }
                    produceLineCore.setSno(sno);
                    produceLineCore.setOperator(loginName);
                    produceLineCoreDao.save(produceLineCore);
                }
            }
        }
    }
}
