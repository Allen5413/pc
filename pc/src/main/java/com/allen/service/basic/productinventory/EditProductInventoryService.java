package com.allen.service.basic.productinventory;

import java.math.BigDecimal;

/**
 * 包路径：com.allen.service.basic.productinventory
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-12 16:29
 */
public interface EditProductInventoryService {
    public void editProductInventory(long productId, BigDecimal productNum);
}
