package com.allen.dao.basic.product;
import com.allen.entity.basic.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lenovo on 2017/2/21.
 */
public interface ProductDao extends CrudRepository<Product, Long> {
    //功能：根据编码查询产品信息
    public List<Product> findByCode(String code);
    //功能：根据名称查询产品信息
    public List<Product> findByName(String name);
}
