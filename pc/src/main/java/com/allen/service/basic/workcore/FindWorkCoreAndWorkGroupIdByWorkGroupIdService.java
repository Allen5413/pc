package com.allen.service.basic.workcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.pojo.workcore.WorkCoreBean;

import java.util.List;

/**
 * 通过工作组id查询工作中心信息
 * 查询结果的工作组id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
public interface FindWorkCoreAndWorkGroupIdByWorkGroupIdService {

    /**
     * 返回所有的工作中心
     * @param workGroupId
     * @return
     * @throws Exception
     */
    public JSONObject find(long workGroupId)throws Exception;

    /**
     * 返回已关联的工作中心
     * @param workGroupId
     * @return
     * @throws Exception
     */
    public List<WorkCoreBean> findWith(long workGroupId)throws Exception;

    /**
     * 返回未关联的工作中心
     * @param workGroupId
     * @return
     * @throws Exception
     */
    public List<WorkCoreBean> findNotWith(long workGroupId)throws Exception;
}
