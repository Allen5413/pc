package com.allen.service.basic.producelinecoreproductcg;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface AddPlcpcgService {
    public void add(long plcpId, String delPlcpcgIds, Long[] plcpcgIds, Integer[] snos, Long[] cgIds, Long[] wmIds,
                    Integer[] unitTimeCapacitys, Integer[] minBatchs, String loginName)throws Exception;
}
