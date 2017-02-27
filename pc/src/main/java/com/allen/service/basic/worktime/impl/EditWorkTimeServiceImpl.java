package com.allen.service.basic.worktime.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.worktime.WorkTimeDao;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.worktime.EditWorkTimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditWorkTimeServiceImpl implements EditWorkTimeService {

    @Resource
    private WorkTimeDao workTimeDao;

    @Override
    public void edit(WorkTime workTime) throws Exception {
        WorkTime oldWorkTime = workTimeDao.findOne(workTime.getId());
        List list = workTimeDao.findByCode(workTime.getCode());
        if(null != list && 0 < list.size() && !oldWorkTime.getCode().equals(workTime.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = workTimeDao.findByName(workTime.getName());
        if(null != list && 0 < list.size() && !oldWorkTime.getName().equals(workTime.getName())){
            throw new BusinessException("名称已存在！");
        }
        list = workTimeDao.findBySno(workTime.getSno());
        if(null != list && 0 < list.size() && oldWorkTime.getSno()!=workTime.getSno()){
            throw new BusinessException("顺序号已存在！");
        }

        oldWorkTime.setCode(workTime.getCode());
        oldWorkTime.setName(workTime.getName());
        oldWorkTime.setBeginTime(workTime.getBeginTime());
        oldWorkTime.setEndTime(workTime.getEndTime());
        oldWorkTime.setOperator(workTime.getOperator());
        oldWorkTime.setOperateTime(workTime.getOperateTime());
        workTimeDao.save(workTime);
    }
}
