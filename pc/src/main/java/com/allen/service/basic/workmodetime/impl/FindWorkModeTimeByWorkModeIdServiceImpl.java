package com.allen.service.basic.workmodetime.impl;

import com.allen.dao.basic.workmodetime.WorkModeTimeDao;
import com.allen.entity.basic.WorkModeTime;
import com.allen.service.basic.workmodetime.FindWorkModeTimeByWorkModeIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.allen.service.basic.workmodetime.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-02-27 23:17
 */
@Service
public class FindWorkModeTimeByWorkModeIdServiceImpl implements FindWorkModeTimeByWorkModeIdService {
    @Resource
    private WorkModeTimeDao workModeTimeDao;
    @Override
    public List<WorkModeTime> findWorkModeTimeByWorkModeId(long workModeId) {
        return workModeTimeDao.findByWorkModeId(workModeId);
    }
}
