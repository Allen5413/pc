package com.allen.service.reportform;

import com.allen.entity.pojo.workgroup.WorkGroupForSchedulBean;

import java.util.List;

/**
 * Created by Allen on 2017/4/20.
 */
public interface FindWorkGroupSchedulService {
    public List<WorkGroupForSchedulBean> find(String startDate, String endDate, String wgId, String name, String coreCode)throws Exception;
}
