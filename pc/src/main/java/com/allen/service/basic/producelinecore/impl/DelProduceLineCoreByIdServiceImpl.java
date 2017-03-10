package com.allen.service.basic.producelinecore.impl;

import com.allen.dao.basic.producelinecore.ProduceLineCoreDao;
import com.allen.dao.basic.producelinecoreproduct.ProduceLineCoreProductDao;
import com.allen.entity.basic.ProduceLineCore;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecore.DelProduceLineCoreByIdService;
import com.allen.service.basic.producelinecoreproduct.DelProduceLineCoreProductByIdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/3/10.
 */
@Service
public class DelProduceLineCoreByIdServiceImpl implements DelProduceLineCoreByIdService {

    @Resource
    private ProduceLineCoreDao produceLineCoreDao;
    @Resource
    private ProduceLineCoreProductDao produceLineCoreProductDao;
    @Resource
    private DelProduceLineCoreProductByIdService delProduceLineCoreProductByIdService;

    @Override
    @Transactional
    public void del(long id) throws Exception {
        //查询关联的产品信息
        List<ProduceLineCoreProduct> produceLineCoreProductList = produceLineCoreProductDao.findByProduceLineCoreId(id);
        if(null != produceLineCoreProductList && 0 < produceLineCoreProductList.size()){
            for(ProduceLineCoreProduct produceLineCoreProduct : produceLineCoreProductList){
                //删除关联的产品信息
                delProduceLineCoreProductByIdService.del(produceLineCoreProduct.getId());
            }
        }
        //删除自身数据
        produceLineCoreDao.delete(id);
    }
}
