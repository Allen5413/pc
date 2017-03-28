package com.allen.service.basic.producelineuse.impl;

import com.allen.dao.basic.producelineuse.ProduceLineUseDao;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.service.basic.producelineuse.DelProduceLineUseService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.allen.service.basic.producelineuse.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-21 21:59
 */
@Service
public class DelProduceLineUseServiceImpl implements DelProduceLineUseService {
    @Resource
    private ProduceLineUseDao produceLineUseDao;
    @Override
    public void delProduceLineUse(String start, String end,long fMaterialId) {
        List<ProduceLineUse> produceLineUses = null;
        if(fMaterialId==0){
            produceLineUses = produceLineUseDao.findByProductionDateBetween(
                    DateUtil.getFormatDate(start,DateUtil.shortDatePattern),
                    DateUtil.getFormatDate(end,DateUtil.shortDatePattern));
        }else{
            produceLineUses = produceLineUseDao.findByProductionDateAndProductId(
                    DateUtil.getFormatDate(start,DateUtil.shortDatePattern),fMaterialId);
        }
        if(produceLineUses!=null&&produceLineUses.size()>0){
            produceLineUseDao.delete(produceLineUses);
        }
    }
}
