package com.allen.service.basic.produceline.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.produceline.FindProduceLineDao;
import com.allen.service.basic.produceline.FindProduceLinePageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindProduceLinePageServiceImpl implements FindProduceLinePageService {

    @Resource
    private FindProduceLineDao findProduceLineDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findProduceLineDao.findPage(pageInfo, params, sortMap);
    }
}
