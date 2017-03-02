package com.allen.service.basic.produceline.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.service.basic.produceline.EditProduceLineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditProduceLineServiceImpl implements EditProduceLineService {

    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    public void edit(ProduceLine produceLine) throws Exception {
        ProduceLine oldProduceLine = produceLineDao.findOne(produceLine.getId());
        List list = produceLineDao.findByCode(produceLine.getCode());
        if(null != list && 0 < list.size() && !oldProduceLine.getCode().equals(produceLine.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = produceLineDao.findByName(produceLine.getName());
        if(null != list && 0 < list.size() && !oldProduceLine.getName().equals(produceLine.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldProduceLine.setCode(produceLine.getCode());
        oldProduceLine.setName(produceLine.getName());
        oldProduceLine.setOperator(produceLine.getOperator());
        oldProduceLine.setOperateTime(produceLine.getOperateTime());
        produceLineDao.save(oldProduceLine);
    }
}
