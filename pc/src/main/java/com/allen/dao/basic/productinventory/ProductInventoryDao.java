package com.allen.dao.basic.productinventory;

import com.allen.entity.basic.ProductInventory;
import org.springframework.data.repository.CrudRepository;

/**
 * 包路径：com.allen.dao.basic.productinventory
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-12 15:06
 */
public interface ProductInventoryDao extends CrudRepository<ProductInventory,Long> {
    /**
     *功能：根据产品id查询库存信息
     * @param productId
     * @return
     */
    public ProductInventory findByProductId(long productId);
}
