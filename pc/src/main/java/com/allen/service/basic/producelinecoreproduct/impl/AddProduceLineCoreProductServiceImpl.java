package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.entity.basic.Product;
import com.allen.service.basic.produceline.EditProduceLineService;
import com.allen.service.basic.produceline.FindProduceLineByIdService;
import com.allen.service.basic.producelinecoreproduct.AddProduceLineCoreProductService;
import com.allen.service.basic.product.FindProductByIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class AddProduceLineCoreProductServiceImpl implements AddProduceLineCoreProductService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;
    @Resource
    private ProduceLineCoreDao produceLineCoreDao;
    @Resource
    private FindProduceLineByIdService findProduceLineByIdService;
    @Resource
    private EditProduceLineService editProduceLineService;
    @Resource
    private FindProductByIdService findProductByIdService;

    @Override
    @Transactional
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
        this.editProduceLineForProductInfo(plId, pId);
        return produceLineCoreProductDao.save(produceLineCoreProduct);
    }

    /**
     * 修改生产线的产品信息字段
     * @param plId
     * @param pId
     * @throws Exception
     */
    private void editProduceLineForProductInfo(long plId, long pId)throws Exception{
        ProduceLine produceLine = findProduceLineByIdService.find(plId);
        Map product = findProductByIdService.find(pId);
        if(null == produceLine){
            throw new BusinessException("没有找到生产线信息");
        }
        if(null == product){
            throw new BusinessException("没有找到产品信息");
        }

        if(StringUtil.isEmpty(produceLine.getProductIds())){
            produceLine.setProductIds(""+pId);
        }else{
            if(produceLine.getProductIds().indexOf(pId+"") < 0) {
                produceLine.setProductIds(produceLine.getProductIds() + "_" + pId);
            }
        }
        if(StringUtil.isEmpty(produceLine.getProductNames())){
            produceLine.setProductNames("" + product.get("FNAME").toString());
        }else{
            if(produceLine.getProductNames().indexOf(product.get("FNAME").toString()) < 0) {
                produceLine.setProductNames(produceLine.getProductNames() + "_" + product.get("FNAME").toString());
            }
        }
        editProduceLineService.edit(produceLine);
    }
}
