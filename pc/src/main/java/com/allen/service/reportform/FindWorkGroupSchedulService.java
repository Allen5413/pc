package com.allen.service.reportform;

import com.allen.entity.pojo.reportform.WorkGroupSchedulBean;

import java.util.List;

/**
 * Created by Allen on 2017/4/20.
 */
public interface FindWorkGroupSchedulService {
    public List<WorkGroupSchedulBean> find(String startDate, String endDate, String wgId)throws Exception;
}
