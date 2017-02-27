package com.allen.service.basic.worktime.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.worktime.WorkTimeDao;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.worktime.AddWorkTimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddWorkTimeServiceImpl implements AddWorkTimeService {

    @Resource
    private WorkTimeDao workTimeDao;

    @Override
    public void add(WorkTime workTime) throws Exception {
        List list = workTimeDao.findByCode(workTime.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = workTimeDao.findByName(workTime.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        list = workTimeDao.findBySno(workTime.getSno());
        if(null != list && 0 < list.size()){
            throw new BusinessException("顺序号已存在！");
        }
        workTimeDao.save(workTime);
    }
}
