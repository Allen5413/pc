package com.allen.service.basic.resource;

import com.allen.entity.basic.Resource;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/29 0029.
 */
public interface FindResourceByMenuIdService {
    public List<Resource> find(Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception;
}
