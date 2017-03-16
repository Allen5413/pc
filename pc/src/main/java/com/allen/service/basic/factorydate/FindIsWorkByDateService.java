package com.allen.service.basic.factorydate;

/**
 * Created by Allen on 2017/3/14 0014.
 */
public interface FindIsWorkByDateService {
    /**
     * @param date          //查询日期
     * @param beginDate     //开始日期
     * @param endDate       //结束日期
     * @return  true 要上班， false 不上班
     * @throws Exception
     */
    public boolean isWork(String date, String beginDate, String endDate)throws Exception;
}
