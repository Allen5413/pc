package com.allen.service.basic.factorydate.impl;

import com.allen.service.basic.factorydate.FindIsWorkByDateService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询一个日期是否需要上班
 * Created by Allen on 2017/3/14 0014.
 */
@Service
public class FindIsWorkByDateServiceImpl implements FindIsWorkByDateService{

    private Map<String, Boolean> dateMap = null;

    /**
     * @param date          //查询日期
     * @param beginDate     //开始日期
     * @param endDate       //结束日期
     * @return
     * @throws Exception
     */
    @Override
    public boolean find(String date, String beginDate, String endDate)throws Exception{
        if(null == dateMap){
            dateMap = new HashMap<String, Boolean>();
        }
        return true;
    }
}
