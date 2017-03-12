package com.allen.service.basic.productinventory.impl;

import com.allen.dao.basic.productinventory.ProductInventoryDao;
import com.allen.entity.basic.ProductInventory;
import com.allen.service.basic.productinventory.FindProInvByPIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 包路径：com.allen.service.basic.productinventory.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-12 16:33
 */
@Service
public class FindProInvByPIdServiceImpl implements FindProInvByPIdService {
    @Resource
    private ProductInventoryDao productInventoryDao;
    @Override
    public ProductInventory findProductInventoryByProductId(long productId) {
        return productInventoryDao.findByProductId(productId);
    }
}
