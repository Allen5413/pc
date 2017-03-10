package com.allen.service.basic.producelinecore;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface AddProduceLineCoreService {
    public void add(long plId, String delPlcIds, Long[] plcIds, Long[] wcIds, Integer[] snos, String loginName)throws Exception;
}
