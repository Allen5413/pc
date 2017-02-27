package com.allen.dao.basic.produceline;

import com.allen.entity.basic.ProduceLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface ProduceLineDao extends CrudRepository<ProduceLine, Long> {

    /**
     * 通过工作中心id查询生产线信息
     * 查询结果的工作中心id字段，如果有值说明有关联，没有值说明没有关联
     * @param workCoreId
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "SELECT pl.id, pl.code, pl.name, plc.produce_line_id " +
            "FROM produce_line pl LEFT JOIN produce_line_core plc ON pl.id = plc.produce_line_id AND plc.work_core_id = ?1 " +
            "ORDER BY pl.code")
    public List<Object[]> findProduceLineAndWorkCoreIdByWorkCoreId(long workCoreId)throws Exception;

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List<ProduceLine> findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List<ProduceLine> findByName(String name);

    @Query("from ProduceLine order by id")
    public List<ProduceLine> findAll();
}
