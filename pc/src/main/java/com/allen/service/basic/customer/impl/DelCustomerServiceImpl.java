package com.allen.service.basic.customer.impl;

import com.allen.dao.basic.customer.CustomerDao;
import com.allen.service.basic.customer.DelCustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/29 0029.
 */
@Service
public class DelCustomerServiceImpl implements DelCustomerService {

    @Resource
    private CustomerDao customerDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        customerDao.delete(id);
    }
}
