package com.allen.service.basic.productinventory.impl;

import com.allen.dao.basic.productinventory.ProductInventoryDao;
import com.allen.entity.basic.ProductInventory;
import com.allen.service.basic.productinventory.EditProductInventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 包路径：com.allen.service.basic.productinventory.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-12 16:32
 */
@Service
public class EditProductInventoryServiceImpl implements EditProductInventoryService {
    @Resource
    private ProductInventoryDao productInventoryDao;
    @Override
    public void editProductInventory(long productId, BigDecimal productNum) {
        ProductInventory productInventory = productInventoryDao.findByProductId(productId);
        productInventory.setProductNum(productNum);
        productInventoryDao.save(productInventory);
    }
}
