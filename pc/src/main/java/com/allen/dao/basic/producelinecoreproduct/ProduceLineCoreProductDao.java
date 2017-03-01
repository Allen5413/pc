package com.allen.dao.basic.producelinecoreproduct;

import com.allen.entity.basic.ProduceLineCoreProduct;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface ProduceLineCoreProductDao extends CrudRepository<ProduceLineCoreProduct, Long> {

    /**
     * 查询一个生产线关联工作中心下面与一个产品的信息
     * @param produceLineCoreId
     * @param productId
     * @return
     * @throws Exception
     */
    public ProduceLineCoreProduct findByProduceLineCoreIdAndProductId(long produceLineCoreId, long productId)throws Exception;

    @Modifying
    @Query("delete from ProduceLineCoreProduct where produceLineCoreId = ?1")
    public void delByProduceLineCoreId(long produceLineCoreId)throws Exception;
}
