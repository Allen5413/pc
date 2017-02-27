package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.AddProduceLineCoreProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class AddProduceLineCoreProductServiceImpl implements AddProduceLineCoreProductService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;
    @Resource
    private ProduceLineCoreDao produceLineCoreDao;

    @Override
    public ProduceLineCoreProduct add(long plId, long wcId, long pId, String loginName) throws Exception {
        //通过生产线id和中心id查询关联信息
        ProduceLineCore produceLineCore = produceLineCoreDao.findByProduceLineIdAndWorkCoreId(plId, wcId);
        if(null == produceLineCore){
            throw new BusinessException("没有找到生产线与工作中心的关联");
        }
        long produceLineCoreId = produceLineCore.getId();
        //先查询产品是否已经关联
        ProduceLineCoreProduct produceLineCoreProduct = produceLineCoreProductDao.findByProduceLineCoreIdAndProductId(produceLineCoreId, pId);
        if(null != produceLineCoreProduct){
            throw new BusinessException("该产品已经添加过了");
        }
        produceLineCoreProduct = new ProduceLineCoreProduct();
        produceLineCoreProduct.setProduceLineCoreId(produceLineCoreId);
        produceLineCoreProduct.setProductId(pId);
        produceLineCoreProduct.setOperator(loginName);
        return produceLineCoreProductDao.save(produceLineCoreProduct);
    }
}
