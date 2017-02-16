package com.allen.service.basic.customer.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.customer.CustomerDao;
import com.allen.entity.basic.Customer;
import com.allen.service.basic.customer.AddCustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddCustomerServiceImpl implements AddCustomerService {

    @Resource
    private CustomerDao customerDao;

    @Override
    public void add(Customer customer) throws Exception {
        List list = customerDao.findByCode(customer.getCode());
        if(null != list && 0 < list.size()){
            throw new BusinessException("编号已存在！");
        }
        list = customerDao.findByName(customer.getName());
        if(null != list && 0 < list.size()){
            throw new BusinessException("名称已存在！");
        }
        customerDao.save(customer);
    }
}
