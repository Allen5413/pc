package com.allen.dao.basic.produceline;

import com.allen.entity.basic.ProduceLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface ProduceLineDao extends CrudRepository<ProduceLine, Long> {

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
}
