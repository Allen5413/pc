package com.allen.service.basic.producelinecoreproduct;

import com.allen.entity.basic.ProduceLineCoreProduct;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface EditProduceLineCoreProductService {
    public ProduceLineCoreProduct edit(ProduceLineCoreProduct produceLineCoreProduct, String loginName)throws Exception;
}
