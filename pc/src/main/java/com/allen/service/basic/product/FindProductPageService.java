package com.allen.service.basic.product;

import com.allen.dao.PageInfo;

import java.util.Map;

/**
 * Created by lenovo on 2017/2/21.
 */
public interface FindProductPageService {
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap)throws Exception;
}
