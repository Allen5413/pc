package com.allen.service.basic.workgroup.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.workgroup.AddWorkGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddWorkGroupServiceImpl implements AddWorkGroupService {

    @Resource
    private WorkGroupDao workGroupDao;

    @Override
    public void add(WorkGroup workGroup) throws Exception {
        List list = workGroupDao.findByCode(workGroup.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = workGroupDao.findByName(workGroup.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        workGroupDao.save(workGroup);
    }
}
