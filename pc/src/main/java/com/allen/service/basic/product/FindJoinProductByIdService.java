package com.allen.service.basic.product;

import com.allen.entity.basic.ProductSelfUse;

import java.util.List;

/**
 * 包路径：com.allen.service.basic.product
 * 功能说明：功能查询产品下的说有关联产品
 * 创建人： ly
 * 创建时间: 2017-03-01 10:35
 */
public interface FindJoinProductByIdService {
    public List<ProductSelfUse> findJoinProductById(long productId);
}
