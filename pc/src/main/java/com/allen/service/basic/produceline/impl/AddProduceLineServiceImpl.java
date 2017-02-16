package com.allen.service.basic.produceline.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.service.basic.produceline.AddProduceLineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddProduceLineServiceImpl implements AddProduceLineService {

    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    public void add(ProduceLine produceLine) throws Exception {
        List list = produceLineDao.findByCode(produceLine.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = produceLineDao.findByName(produceLine.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        produceLineDao.save(produceLine);
    }
}
