package com.allen.service.basic.producelinecoreproduct;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface AddProduceLineCoreProductService {
    public void add(long plId, long wcId, String delPlcpIds, Long[] plcpIds, Long[] pIds, Integer[] qualifiedRates, String loginName)throws Exception;
}
