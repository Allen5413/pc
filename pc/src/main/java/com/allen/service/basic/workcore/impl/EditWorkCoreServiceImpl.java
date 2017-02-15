package com.allen.service.basic.workcore.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.menu.FindMenuDao;
import com.allen.dao.basic.menu.MenuDao;
import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.entity.basic.Menu;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.menu.EditMenuService;
import com.allen.service.basic.workcore.EditWorkCoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditWorkCoreServiceImpl implements EditWorkCoreService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    public void edit(WorkCore workCore) throws Exception {
        WorkCore oldWorkCore = workCoreDao.findOne(workCore.getId());
        List list = workCoreDao.findByCode(workCore.getCode());
        if(null != list && 0 < list.size() && !oldWorkCore.getCode().equals(workCore.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = workCoreDao.findByName(workCore.getName());
        if(null != list && 0 < list.size() && !oldWorkCore.getName().equals(workCore.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldWorkCore.setCode(workCore.getCode());
        oldWorkCore.setName(workCore.getName());
        oldWorkCore.setIsPublic(workCore.getIsPublic());
        oldWorkCore.setOperator(workCore.getOperator());
        oldWorkCore.setOperateTime(workCore.getOperateTime());
        workCoreDao.save(oldWorkCore);
    }
}
