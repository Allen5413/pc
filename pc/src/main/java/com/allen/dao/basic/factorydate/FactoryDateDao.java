package com.allen.dao.basic.factorydate;

import com.allen.entity.basic.FactoryDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 2017/3/16 0016.
 */
public interface FactoryDateDao extends CrudRepository<FactoryDate, Long> {

    /**
     * 查询一个时间段内的工厂日历
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @Query("FROM FactoryDate where FDAY BETWEEN ?1 and ?2")
    public List<FactoryDate> findByBeginDateAndEndDate(Date beginDate, Date endDate)throws Exception;
}
