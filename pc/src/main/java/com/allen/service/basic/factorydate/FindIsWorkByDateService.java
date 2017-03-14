package com.allen.service.basic.factorydate;

/**
 * Created by Allen on 2017/3/14 0014.
 */
public interface FindIsWorkByDateService {
    public boolean find(String date, String beginDate, String endDate)throws Exception;
}
