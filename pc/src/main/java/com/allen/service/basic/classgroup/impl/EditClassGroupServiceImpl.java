package com.allen.service.basic.classgroup.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.classgroup.ClassGroupDao;
import com.allen.entity.basic.ClassGroup;
import com.allen.service.basic.classgroup.EditClassGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditClassGroupServiceImpl implements EditClassGroupService {

    @Resource
    private ClassGroupDao classGroupDao;

    @Override
    public void edit(ClassGroup classGroup) throws Exception {
        ClassGroup oldClassGroup = classGroupDao.findOne(classGroup.getId());
        List list = classGroupDao.findByCode(classGroup.getCode());
        if(null != list && 0 < list.size() && !oldClassGroup.getCode().equals(classGroup.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = classGroupDao.findByName(classGroup.getName());
        if(null != list && 0 < list.size() && !oldClassGroup.getName().equals(classGroup.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldClassGroup.setCode(classGroup.getCode());
        oldClassGroup.setName(classGroup.getName());
        oldClassGroup.setOperator(classGroup.getOperator());
        oldClassGroup.setOperateTime(classGroup.getOperateTime());
        classGroupDao.save(oldClassGroup);
    }
}
