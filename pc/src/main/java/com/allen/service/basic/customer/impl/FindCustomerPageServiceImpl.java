package com.allen.service.basic.customer.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.customer.FindCustomerDao;
import com.allen.service.basic.customer.FindCustomerPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindCustomerPageServiceImpl implements FindCustomerPageService {

    @Resource
    private FindCustomerDao findCustomerDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        return findCustomerDao.findPage(pageInfo, params, sortMap);
    }
}
