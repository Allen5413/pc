package com.allen.service.basic.workmode.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.workmode.WorkModeDao;
import com.allen.dao.basic.workmodetime.WorkModeTimeDao;
import com.allen.entity.basic.WorkModeTime;
import com.allen.entity.basic.WorkMode;
import com.allen.entity.basic.WorkModeTime;
import com.allen.service.basic.workmode.EditWorkModeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditWorkModeServiceImpl implements EditWorkModeService {

    @Resource
    private WorkModeDao workModeDao;
    @Resource
    private WorkModeTimeDao workModeTimeDao;
    @Override
    @Transactional
    public void edit(WorkMode workMode) throws Exception {
        WorkMode oldWorkMode = workModeDao.findOne(workMode.getId());
        List list = workModeDao.findByCode(workMode.getCode());
        if(null != list && 0 < list.size() && !oldWorkMode.getCode().equals(workMode.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = workModeDao.findByName(workMode.getName());
        if(null != list && 0 < list.size() && !oldWorkMode.getName().equals(workMode.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldWorkMode.setCode(workMode.getCode());
        oldWorkMode.setName(workMode.getName());
        oldWorkMode.setOperator(workMode.getOperator());
        oldWorkMode.setOperateTime(workMode.getOperateTime());
        workModeDao.save(oldWorkMode);
        List<WorkModeTime> oldWorkModeTimes = workModeTimeDao.findByWorkModeId(oldWorkMode.getId());
        if(oldWorkModeTimes==null||oldWorkModeTimes.size()==0){//添加新的
            if(workMode.getWorkModeTimeList()!=null&&workMode.getWorkModeTimeList().size()>0){
                for (WorkModeTime workModeTime:workMode.getWorkModeTimeList()){
                    workModeTime.setWorkModeId(oldWorkMode.getId());
                    workModeTime.setCreator(oldWorkMode.getOperator());
                    workModeTime.setOperator(oldWorkMode.getOperator());
                    workModeTimeDao.save(workModeTime);
                }
            }
        }else if(workMode.getWorkModeTimeList()==null||workMode.getWorkModeTimeList().size()==0){
            if(oldWorkModeTimes!=null&&oldWorkModeTimes.size()>0) {//全部删除
                for (WorkModeTime workModeTime:oldWorkModeTimes){
                    workModeTimeDao.delete(workModeTime);
                }
            }
        }else{
            boolean flag = false;
            for (WorkModeTime currentWorkModeTime:oldWorkModeTimes){
                flag = false;
                for (WorkModeTime workModeTime:workMode.getWorkModeTimeList()){
                    if(currentWorkModeTime.getWorkTimeId()==workModeTime.getWorkTimeId()){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    workModeTimeDao.delete(currentWorkModeTime);
                }
            }
            for (WorkModeTime operateWorkModeTime:workMode.getWorkModeTimeList()){
                flag = false;
                operateWorkModeTime.setOperator(oldWorkMode.getOperator());
                for (WorkModeTime currentWorkModeTime:oldWorkModeTimes){
                    if(operateWorkModeTime.getWorkTimeId()==currentWorkModeTime.getWorkTimeId()){
                        flag = true;
                        operateWorkModeTime.setCreator(currentWorkModeTime.getCreator());
                        operateWorkModeTime.setCreateTime(currentWorkModeTime.getCreateTime());
                        operateWorkModeTime.setId(currentWorkModeTime.getId());
                        break;
                    }
                }
                if(!flag){
                    operateWorkModeTime.setCreator(oldWorkMode.getCreator());
                }
                workModeTimeDao.save(operateWorkModeTime);
            }
        }
    }
}
