package com.allen.dao.basic.productselfuse;
import com.allen.entity.basic.ProductSelfUse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2017/2/21.
 */
@Service
public interface ProductSelfUseDao  extends CrudRepository<ProductSelfUse, Long> {

}
