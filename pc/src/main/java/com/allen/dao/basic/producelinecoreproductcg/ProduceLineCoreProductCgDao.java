package com.allen.dao.basic.producelinecoreproductcg;

import com.allen.entity.basic.ProduceLineCoreProductCg;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Allen on 2017/3/9.
 */
public interface ProduceLineCoreProductCgDao extends CrudRepository<ProduceLineCoreProductCg, Long> {

    /**
     * 删除一个生产线-中心-产品下的所有班组关联
     * @param plcpId
     * @throws Exception
     */
    @Modifying
    @Query("delete from ProduceLineCoreProductCg where produceLineCoreProductId = ?1")
    public void delByPlcpId(long plcpId)throws Exception;
}
