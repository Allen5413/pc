package com.allen.service.basic.classgroup.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.classgroup.ClassGroupDao;
import com.allen.entity.basic.ClassGroup;
import com.allen.service.basic.classgroup.AddClassGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddClassGroupServiceImpl implements AddClassGroupService {

    @Resource
    private ClassGroupDao classGroupDao;

    @Override
    public void add(ClassGroup classGroup) throws Exception {
        List list = classGroupDao.findByCode(classGroup.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = classGroupDao.findByName(classGroup.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        classGroupDao.save(classGroup);
    }
}
