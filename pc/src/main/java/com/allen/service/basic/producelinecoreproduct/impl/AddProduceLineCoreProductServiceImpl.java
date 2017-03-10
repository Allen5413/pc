package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.dao.basic.producelinecoreproduct.FindProduceLineCoreProductDao;
import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.entity.basic.ProduceLineCoreProduct;
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
    @Resource
    private DelProduceLineCoreProductByIdServiceImpl delProduceLineCoreProductByIdService;

    @Override
    @Transactional
    public void add(long plId, long wcId, String delPlcpIds, Long[] plcpIds, Long[] pIds, Float[] qualifiedRates, String loginName) throws Exception {
        //通过生产线id和中心id查询关联信息
        ProduceLineCore produceLineCore = produceLineCoreDao.findByProduceLineIdAndWorkCoreId(plId, wcId);
        if(null == produceLineCore){
            throw new BusinessException("没有找到生产线与工作中心的关联");
        }
        //删掉的产品
        if(!StringUtil.isEmpty(delPlcpIds)){
            for(String delPlcpId : delPlcpIds.split(",")){
                delProduceLineCoreProductByIdService.del(Long.parseLong(delPlcpId));
            }
        }
        if(null != plcpIds && 0 < plcpIds.length) {
            long plcId = produceLineCore.getId();
            for(int i=0; i < plcpIds.length; i++) {
                long plcpId = plcpIds[i];
                long pId = pIds[i];
                float qualifiedRate = qualifiedRates[i];

                //==0说明是新增的产品信息，否则就是之前关联的产品
                if(0 == plcpId){
                    ProduceLineCoreProduct produceLineCoreProduct = new ProduceLineCoreProduct();
                    produceLineCoreProduct.setProduceLineCoreId(plcId);
                    produceLineCoreProduct.setProductId(pId);
                    produceLineCoreProduct.setQualifiedRate(qualifiedRate);
                    produceLineCoreProduct.setOperator(loginName);
                    this.editProduceLineForProductInfoForAdd(plId, pId);
                    produceLineCoreProductDao.save(produceLineCoreProduct);
                }else {
                    ProduceLineCoreProduct produceLineCoreProduct = produceLineCoreProductDao.findOne(plcpId);
                    //如果产品没变，只修改合格率；否则删除产品信息重新关联
                    if(produceLineCoreProduct.getProductId() != pId){
                        delProduceLineCoreProductByIdService.del(produceLineCoreProduct.getId());
                        produceLineCoreProduct = new ProduceLineCoreProduct();
                        produceLineCoreProduct.setProduceLineCoreId(plcId);
                        produceLineCoreProduct.setProductId(pId);
                        this.editProduceLineForProductInfoForAdd(plId, pId);
                    }
                    produceLineCoreProduct.setQualifiedRate(qualifiedRate);
                    produceLineCoreProduct.setOperator(loginName);
                    produceLineCoreProductDao.save(produceLineCoreProduct);
                }
            }
        }
    }

    /**
     * 修改生产线的产品信息字段
     * @param plId
     * @param pId
     * @throws Exception
     */
    private void editProduceLineForProductInfoForAdd(long plId, long pId)throws Exception{
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
