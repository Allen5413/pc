package com.allen.service.basic.producelinecore;

/**
 * Created by Allen on 2017/2/15 0015.
 */
public interface SetProduceLineCoreService {

    /**
     * 通过工作中心选生产线来设置关联
     * @param wcId
     * @param plIds
     * @param loginName
     * @throws Exception
     */
    public void setForCore(long wcId, Long[] plIds, String loginName)throws Exception;

    /**
     * 通过生产线选中心来设置关联
     * @param plId
     * @param wcIds
     * @param loginName
     * @throws Exception
     */
    public void setForLine(long plId, Long[] wcIds, String loginName)throws Exception;
}
