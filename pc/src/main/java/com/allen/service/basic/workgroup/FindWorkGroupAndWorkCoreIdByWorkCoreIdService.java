package com.allen.service.basic.workgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.pojo.workgroup.WorkGroupBean;

import java.util.List;

/**
 * 通过工作中心id查询工作组信息
 * 查询结果的工作id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
public interface FindWorkGroupAndWorkCoreIdByWorkCoreIdService {

    /**
     * 返回所有的工作组
     * @param workCoreId
     * @return
     * @throws Exception
     */
    public JSONObject find(long workCoreId)throws Exception;

    /**
     * 返回已关联的工作组
     * @param workGroupId
     * @return
     * @throws Exception
     */
    public List<WorkGroupBean> findWith(long workGroupId)throws Exception;

    /**
     * 返回未关联的工作组
     * @param workGroupId
     * @return
     * @throws Exception
     */
    public List<WorkGroupBean> findNotWith(long workGroupId)throws Exception;
}
