package com.allen.dao.basic.classgroup;

import com.allen.entity.basic.ClassGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Allen on 2017/2/14 0014.
 */
public interface ClassGroupDao extends CrudRepository<ClassGroup, Long> {

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public List<ClassGroup> findByCode(String code);

    /**
     * 通过name查询
     * @param name
     * @return
     */
    public List<ClassGroup> findByName(String name);
}
