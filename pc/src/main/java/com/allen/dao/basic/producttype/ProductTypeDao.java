package com.allen.dao.basic.producttype;

import com.allen.entity.basic.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface ProductTypeDao extends CrudRepository<ProductType, Long> {

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List<ProductType> findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List<ProductType> findByName(String name);
}
