package com.allen.service.basic.produceline;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.pojo.produceline.ProduceLineBean;

import java.util.List;

/**
 * 通过工作中心id查询生产线信息
 * 查询结果的工作中心id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
public interface FindProduceLineAndWorkCoreIdByWorkCoreIdService {

    /**
     * 返回所有的生产线
     * @param workCoreId
     * @return
     * @throws Exception
     */
    public JSONObject find(long workCoreId)throws Exception;

    /**
     * 返回已关联的生产线
     * @param workCoreId
     * @return
     * @throws Exception
     */
    public List<ProduceLineBean> findWith(long workCoreId)throws Exception;

    /**
     * 返回未关联的生产线
     * @param workCoreId
     * @return
     * @throws Exception
     */
    public List<ProduceLineBean> findNotWith(long workCoreId)throws Exception;
}
