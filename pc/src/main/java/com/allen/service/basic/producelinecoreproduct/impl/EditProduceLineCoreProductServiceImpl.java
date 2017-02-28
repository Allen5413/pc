package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.EditProduceLineCoreProductService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/28.
 */
@Service
public class EditProduceLineCoreProductServiceImpl implements EditProduceLineCoreProductService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;

    @Override
    public ProduceLineCoreProduct edit(ProduceLineCoreProduct produceLineCoreProduct, String loginName) throws Exception {
        produceLineCoreProduct.setOperator(loginName);
        produceLineCoreProduct.setOperateTime(DateUtil.getLongNowTime());
        return produceLineCoreProductDao.save(produceLineCoreProduct);
    }
}
