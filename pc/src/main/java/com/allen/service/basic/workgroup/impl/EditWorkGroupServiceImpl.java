package com.allen.service.basic.workgroup.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.workgroup.EditWorkGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditWorkGroupServiceImpl implements EditWorkGroupService {

    @Resource
    private WorkGroupDao workGroupDao;

    @Override
    public void edit(WorkGroup workGroup) throws Exception {
        WorkGroup oldWorkGroup = workGroupDao.findOne(workGroup.getId());
        List list = workGroupDao.findByCode(workGroup.getCode());
        if(null != list && 0 < list.size() && !oldWorkGroup.getCode().equals(workGroup.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = workGroupDao.findByName(workGroup.getName());
        if(null != list && 0 < list.size() && !oldWorkGroup.getName().equals(workGroup.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldWorkGroup.setCode(workGroup.getCode());
        oldWorkGroup.setName(workGroup.getName());
        oldWorkGroup.setOperator(workGroup.getOperator());
        oldWorkGroup.setOperateTime(workGroup.getOperateTime());
        workGroupDao.save(oldWorkGroup);
    }
}
