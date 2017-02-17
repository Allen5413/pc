package com.allen.service.basic.produceline;

import com.alibaba.fastjson.JSONObject;

/**
 * 通过工作中心id查询工作组信息
 * 查询结果的工作id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
public interface FindProduceLineAndWorkCoreIdByWorkCoreIdService {

    /**
     * 返回所有的工作组
     * @param workCoreId
     * @return
     * @throws Exception
     */
    public JSONObject find(long workCoreId)throws Exception;
}
