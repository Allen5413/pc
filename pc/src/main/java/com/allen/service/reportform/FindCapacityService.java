package com.allen.service.reportform;

import com.allen.entity.pojo.workgroup.WorkGroupForCapacityBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/21.
 */
public interface FindCapacityService {
    public List<WorkGroupForCapacityBean> find(Map<String, String> params)throws Exception;
}
