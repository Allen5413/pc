package com.allen.service.basic.producttype.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.producttype.FindProductTypeDao;
import com.allen.service.basic.producttype.FindProductTypePageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindProductTypePageServiceImpl implements FindProductTypePageService {

    @Resource
    private FindProductTypeDao findProductTypeDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findProductTypeDao.findPage(pageInfo, params, sortMap);
    }
}
