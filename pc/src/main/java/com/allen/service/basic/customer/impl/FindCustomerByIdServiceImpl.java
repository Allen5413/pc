package com.allen.service.basic.customer.impl;

import com.allen.dao.basic.customer.CustomerDao;
import com.allen.entity.basic.Customer;
import com.allen.service.basic.customer.FindCustomerByIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
@Service
public class FindCustomerByIdServiceImpl implements FindCustomerByIdService {

    @Resource
    private CustomerDao customerDao;

    @Override
    public Customer find(long id) throws Exception {
        return customerDao.findOne(id);
    }
}
