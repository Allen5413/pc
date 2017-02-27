package com.allen.service.basic.producelinecoreproduct;

import com.allen.entity.basic.ProduceLineCoreProduct;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface AddProduceLineCoreProductService {
    public ProduceLineCoreProduct add(long plId, long wcId, long pId, String loginName)throws Exception;
}
