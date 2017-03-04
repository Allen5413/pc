package com.allen.service.basic.producelinecoreproduct.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.dao.basic.product.FindProductDao;
import com.allen.entity.basic.ProduceLine;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.entity.basic.Product;
import com.allen.service.basic.producelinecoreproduct.SetProduceLineCoreProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class SetProduceLineCoreProductServiceImpl implements SetProduceLineCoreProductService {

    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;
    @Resource
    private ProduceLineCoreDao produceLineCoreDao;
    @Resource
    private FindProductDao findProductDao;
    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    @Transactional
    public void set(long plId, long wcId, Long[] pIds, Long[] wmIds,
                    Integer[] unitTimeCapacitys, Integer[] qualifiedRates, Integer[] minBatchs, String loginName) throws Exception {
        //通过生产线id和中心id查询关联信息
        ProduceLineCore produceLineCore = produceLineCoreDao.findByProduceLineIdAndWorkCoreId(plId, wcId);
        if(null == produceLineCore){
            throw new BusinessException("没有找到生产线与工作中心的关联");
        }
        long produceLineCoreId = produceLineCore.getId();
        //先删掉所有产品关联
        produceLineCoreProductDao.delByProduceLineCoreId(produceLineCoreId);
        //重新保存新的产品
        if(null != pIds && 0 < pIds.length){
            List<ProduceLineCoreProduct> list = new ArrayList<ProduceLineCoreProduct>(pIds.length);
            for(int i=0; i<pIds.length; i++){
                long pId = pIds[i];
                long wmId = wmIds[i];
                int unitTimeCapacity = unitTimeCapacitys[i];
                int qualifiedRate = qualifiedRates[i];
                int minBatch = minBatchs[i];

                ProduceLineCoreProduct produceLineCoreProduct = new ProduceLineCoreProduct();
                produceLineCoreProduct.setProduceLineCoreId(produceLineCoreId);
                produceLineCoreProduct.setProductId(pId);
                produceLineCoreProduct.setWorkModeId(wmId);
                produceLineCoreProduct.setUnitTimeCapacity(unitTimeCapacity*60*60);
                produceLineCoreProduct.setQualifiedRate(qualifiedRate);
                produceLineCoreProduct.setMinBatch(minBatch);
                produceLineCoreProduct.setOperator(loginName);
                list.add(produceLineCoreProduct);
            }
            produceLineCoreProductDao.save(list);
        }
        this.setProduceLineForProduct(plId);
    }

    /**
     * 设置产品线关联的产品信息字段到产品线表上，方便于查询显示
     * @param plId
     * @throws Exception
     */
    private void setProduceLineForProduct(long plId)throws Exception{
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("plc.produce_line_id", plId);
        List<Map> productList = findProductDao.findByPlId(params);
        if(null != productList && 0 < productList.size()){
            ProduceLine produceLine = produceLineDao.findOne(plId);
            String ids = "", names = "";
            for(Map product : productList){
                ids += product.get("FMATERIALID")+"_";
                names += product.get("FNAME")+"_";
            }
            ids = ids.substring(0, ids.length()-1);
            names = names.substring(0, names.length()-1);
            produceLine.setProductIds(ids);
            produceLine.setProductNames(names);
            produceLineDao.save(produceLine);
        }
    }
}
