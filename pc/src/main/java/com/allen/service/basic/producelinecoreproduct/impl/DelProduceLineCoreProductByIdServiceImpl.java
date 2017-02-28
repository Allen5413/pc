package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.dao.basic.producelinecoreproduct.FindProduceLineCoreProductDao;
import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.entity.basic.Product;
import com.allen.service.basic.produceline.EditProduceLineService;
import com.allen.service.basic.produceline.FindProduceLineByIdService;
import com.allen.service.basic.producelinecoreproduct.DelProduceLineCoreProductByIdService;
import com.allen.service.basic.product.FindProductByIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class DelProduceLineCoreProductByIdServiceImpl implements DelProduceLineCoreProductByIdService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;
    @Resource
    private FindProduceLineByIdService findProduceLineByIdService;
    @Resource
    private EditProduceLineService editProduceLineService;
    @Resource
    private FindProductByIdService findProductByIdService;
    @Resource
    private ProduceLineCoreDao produceLineCoreDao;
    @Resource
    private FindProduceLineCoreProductDao findProduceLineCoreProductDao;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        ProduceLineCoreProduct produceLineCoreProduct = produceLineCoreProductDao.findOne(id);
        if(null == produceLineCoreProduct){
            throw new BusinessException("没有找到关联产品信息");
        }
        ProduceLineCore produceLineCore = produceLineCoreDao.findOne(produceLineCoreProduct.getProduceLineCoreId());
        if(null == produceLineCore){
            throw new BusinessException("没有找到关联工作中心信息");
        }
        this.editProduceLineForProductInfo(produceLineCore.getProduceLineId(), produceLineCoreProduct.getProductId());
        produceLineCoreProductDao.delete(id);
    }

    /**
     * 修改生产线的产品信息字段
     * @param plId
     * @param pId
     * @throws Exception
     */
    private void editProduceLineForProductInfo(long plId, long pId)throws Exception{
        ProduceLine produceLine = findProduceLineByIdService.find(plId);
        Product product = findProductByIdService.find(pId);
        if(null == produceLine){
            throw new BusinessException("没有找到生产线信息");
        }
        if(null == product){
            throw new BusinessException("没有找到产品信息");
        }

        //查找该生产线下面是否只有一个中心在关联该产品，是就要删除，有多个的话就不删除
        List<ProduceLineCoreProduct> produceLineCoreProductList = findProduceLineCoreProductDao.findByPlIdAndPId(plId, pId);
        if(null != produceLineCoreProductList && 1 == produceLineCoreProductList.size()) {
            if (produceLine.getProductIds().indexOf(pId + "") == 0) {
                if (produceLine.getProductIds().indexOf("_") < 0) {
                    produceLine.setProductIds(produceLine.getProductIds().replace(pId + "", ""));
                } else {
                    produceLine.setProductIds(produceLine.getProductIds().replace(pId + "_", ""));
                }
            } else {
                produceLine.setProductIds(produceLine.getProductIds().replace("_" + pId, ""));
            }

            if (produceLine.getProductNames().indexOf(product.getName()) == 0) {
                if (produceLine.getProductNames().indexOf("_") < 0) {
                    produceLine.setProductNames(produceLine.getProductNames().replace(product.getName(), ""));
                } else {
                    produceLine.setProductNames(produceLine.getProductNames().replace(product.getName() + "_", ""));
                }
            } else {
                produceLine.setProductNames(produceLine.getProductNames().replace("_" + product.getName(), ""));
            }
            editProduceLineService.edit(produceLine);
        }
    }
}
