package com.allen.service.basic.customer;

import com.allen.entity.basic.Customer;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindCustomerByIdService {
    public Customer find(long id)throws Exception;
}
