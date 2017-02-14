package com.allen.service.basic.workcore.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.workcore.AddWorkCoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddWorkCoreServiceImpl implements AddWorkCoreService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    public void add(WorkCore workCore) throws Exception {
        List list = workCoreDao.findByCode(workCore.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = workCoreDao.findByName(workCore.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        workCoreDao.save(workCore);
    }
}
