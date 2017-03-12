package com.allen.service.basic.productinventory;

import com.allen.entity.basic.ProductInventory;

/**
 * 包路径：com.allen.service.basic.productinventory
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-12 16:30
 */
public interface FindProInvByPIdService {
    public ProductInventory findProductInventoryByProductId(long productId);
}
