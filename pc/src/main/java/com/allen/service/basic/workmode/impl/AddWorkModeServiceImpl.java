package com.allen.service.basic.workmode.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.workmode.WorkModeDao;
import com.allen.dao.basic.workmodetime.WorkModeTimeDao;
import com.allen.entity.basic.WorkMode;
import com.allen.entity.basic.WorkModeTime;
import com.allen.service.basic.workmode.AddWorkModeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddWorkModeServiceImpl implements AddWorkModeService {

    @Resource
    private WorkModeDao workModeDao;
    @Resource
    private WorkModeTimeDao workModeTimeDao;
    @Override
    @Transactional
    public void add(WorkMode workMode) throws Exception {
        List list = workModeDao.findByCode(workMode.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = workModeDao.findByName(workMode.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        WorkMode workModeNew = workModeDao.save(workMode);
        if(workMode.getWorkModeTimeList()!=null&&workMode.getWorkModeTimeList().size()>0){
            for (WorkModeTime workModeTime:workMode.getWorkModeTimeList()){
                workModeTime.setWorkModeId(workModeNew.getId());
                workModeTimeDao.save(workModeTime);
            }
        }
    }
}
