package com.allen.service.basic.producelinecoreproduct;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface SetProduceLineCoreProductService {
    public void set(long plId, long wcId, Long[] pIds, Long[] wmIds,
                    Integer[] unitTimeCapacitys, Integer[] qualifiedRates, Integer[] minBatchs, String loginName)throws Exception;
}
