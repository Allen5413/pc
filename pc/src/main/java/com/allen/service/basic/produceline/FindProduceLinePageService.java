package com.allen.service.basic.produceline;

import com.allen.dao.PageInfo;

import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindProduceLinePageService {
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap)throws Exception;
}
