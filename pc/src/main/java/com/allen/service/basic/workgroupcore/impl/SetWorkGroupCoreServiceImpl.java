package com.allen.service.basic.workgroupcore.impl;

import com.allen.dao.basic.workgroupcore.WorkGroupCoreDao;
import com.allen.entity.basic.WorkGroupCore;
import com.allen.service.basic.workgroupcore.SetWorkGroupCoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/15 0015.
 */
@Service
public class SetWorkGroupCoreServiceImpl implements SetWorkGroupCoreService {

    @Resource
    private WorkGroupCoreDao workGroupCoreDao;

    @Override
    @Transactional
    public void setForCore(long wcId, Long[] wgIds, String loginName) throws Exception {
        //先删除之前的关联
        workGroupCoreDao.delByWorkCoreId(wcId);
        //添加新的关联
        if(null != wgIds && 0 < wgIds.length){
            for (Long wgId : wgIds){
                WorkGroupCore workGroupCore = new WorkGroupCore();
                workGroupCore.setWorkCoreId(wcId);
                workGroupCore.setWorkGroupId(wgId);
                workGroupCore.setOperator(loginName);
                workGroupCoreDao.save(workGroupCore);
            }
        }
    }

    @Override
    @Transactional
    public void setForGroup(long wgId, Long[] wcIds, String loginName) throws Exception {
        //先删除之前的关联
        workGroupCoreDao.delByWorkGroupId(wgId);
        //添加新的关联
        if(null != wcIds && 0 < wcIds.length){
            for (Long wcId : wcIds){
                WorkGroupCore workGroupCore = new WorkGroupCore();
                workGroupCore.setWorkCoreId(wcId);
                workGroupCore.setWorkGroupId(wgId);
                workGroupCore.setOperator(loginName);
                workGroupCoreDao.save(workGroupCore);
            }
        }
    }
}
