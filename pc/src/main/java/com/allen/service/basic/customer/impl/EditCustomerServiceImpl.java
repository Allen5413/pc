package com.allen.service.basic.customer.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.customer.CustomerDao;
import com.allen.entity.basic.Customer;
import com.allen.service.basic.customer.EditCustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditCustomerServiceImpl implements EditCustomerService {

    @Resource
    private CustomerDao customerDao;

    @Override
    public void edit(Customer customer) throws Exception {
        Customer oldCustomer = customerDao.findOne(customer.getId());
        List list = customerDao.findByCode(customer.getCode());
        if(null != list && 0 < list.size() && !oldCustomer.getCode().equals(customer.getCode())){
            throw new BusinessException("编号已存在！");
        }
        list = customerDao.findByName(customer.getName());
        if(null != list && 0 < list.size() && !oldCustomer.getName().equals(customer.getName())){
            throw new BusinessException("名称已存在！");
        }

        oldCustomer.setCode(customer.getCode());
        oldCustomer.setName(customer.getName());
        oldCustomer.setOperator(customer.getOperator());
        oldCustomer.setOperateTime(customer.getOperateTime());
        customerDao.save(customer);
    }
}
