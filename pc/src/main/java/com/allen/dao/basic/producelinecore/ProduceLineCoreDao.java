package com.allen.dao.basic.producelinecore;

import com.allen.entity.basic.ProduceLineCore;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface ProduceLineCoreDao extends CrudRepository<ProduceLineCore, Long> {

    /**
     * 删除一个工作中心的所有关联
     * @param workCoreId
     * @throws Exception
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from produce_line_core where work_core_id = ?1")
    public void delByWorkCoreId(long workCoreId)throws Exception;

    /**
     * 删除一个生产线的所有关联
     * @param produceLineId
     * @throws Exception
     */
    @Modifying
    @Query(nativeQuery = true, value = "delete from produce_line_core where produce_line_id = ?1")
    public void delByProduceLineId(long produceLineId)throws Exception;

    /**
     * 通过生产线id和中心id查询关联信息
     * @param plId
     * @param wcId
     * @return
     * @throws Exception
     */
    public ProduceLineCore findByProduceLineIdAndWorkCoreId(long plId, long wcId)throws Exception;

    /**
     * 通过生产线id查询所有关联中心的关联信息
     * @param plId
     * @return
     * @throws Exception
     */
    public List<ProduceLineCore> findByProduceLineId(long plId)throws Exception;
}
