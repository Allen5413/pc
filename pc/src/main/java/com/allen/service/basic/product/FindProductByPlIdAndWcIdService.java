package com.allen.service.basic.product;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
public interface FindProductByPlIdAndWcIdService {
    public List<Map> find(Map<String,Object> queryParams) throws Exception;
}
