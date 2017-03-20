package com.allen.service.basic.producelineuse.impl;

import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.dao.basic.producelineuse.ProduceLineUseDao;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.service.basic.producelineuse.AddProduceLineUseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.allen.service.basic.producelineuse.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-09 14:53
 */
@Service
public class AddProduceLineUseServiceImpl implements AddProduceLineUseService{

    @Resource
    private ProduceLineUseDao produceLineUseDao;
    @Resource
    private FindProduceLineUseDao findProduceLineUseDao;
    @Override
    @Transactional
    public void addProduceLineUse(ProduceLineUse produceLineUse) {
        //查找同一天 同一生产线 生产中心 班组
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("productionDate",produceLineUse.getProductionDate());//生产日期
        params.put("produceLineId",produceLineUse.getProduceLineId());//生产线id
        params.put("workCoreId",produceLineUse.getWorkCoreId());//工作中心id
        params.put("workTeamId",produceLineUse.getWorkTeamId());//班组id
        params.put("customerId",produceLineUse.getCustomerId());//客户id
        params.put("productId",produceLineUse.getProductId());//产品id
        params.put("work_time_id",produceLineUse.getWorkTimeId());//产班次
        List<ProduceLineUse> produceLineUses = findProduceLineUseDao.findProduceLineUse(params);
        if(produceLineUses==null||produceLineUses.size()==0){
            produceLineUseDao.save(produceLineUse);
        }else{
            ProduceLineUse oldUse = produceLineUses.get(0);
            oldUse.setCapacity(produceLineUse.getCapacity().add(oldUse.getCapacity()));
            oldUse.setPlanQuantity(produceLineUse.getPlanQuantity().add(oldUse.getPlanQuantity()));
            oldUse.setBalanceCapacity(produceLineUse.getBalanceCapacity());
            oldUse.setBalanceTime(produceLineUse.getBalanceTime());
            oldUse.setAddTime(produceLineUse.getAddTime());
            produceLineUseDao.save(oldUse);
        }

    }
}
