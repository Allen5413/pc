package com.allen.dao.basic.product;
import com.allen.entity.basic.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by lenovo on 2017/2/21.
 */
public interface ProductDao extends CrudRepository<Product, Long> {

}
