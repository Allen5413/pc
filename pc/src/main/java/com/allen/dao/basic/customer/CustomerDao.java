package com.allen.dao.basic.customer;

import com.allen.entity.basic.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface CustomerDao extends CrudRepository<Customer, Long> {

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List<Customer> findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List<Customer> findByName(String name);
}
