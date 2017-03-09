package com.allen.service.basic.producelinecoreproductcg;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/21 0021.
 */
public interface FindPlcpcgByPlcpIdService {
    public List<Map> find(long plcpId)throws Exception;
}
