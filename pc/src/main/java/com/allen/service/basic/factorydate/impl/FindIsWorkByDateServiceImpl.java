package com.allen.service.basic.factorydate.impl;

import com.allen.dao.basic.factorydate.FactoryDateDao;
import com.allen.entity.basic.FactoryDate;
import com.allen.service.basic.factorydate.FindIsWorkByDateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 查询一个日期是否需要上班
 * Created by Allen on 2017/3/14 0014.
 */
@Service
public class FindIsWorkByDateServiceImpl implements FindIsWorkByDateService{

    @Resource
    private FactoryDateDao factoryDateDao;

    private List<FactoryDate> factoryDateList = null;

    /**
     * @param date          //查询日期
     * @param beginDate     //开始日期
     * @param endDate       //结束日期
     * @return
     * @throws Exception
     */
    @Override
    public boolean isWork(String date, String beginDate, String endDate)throws Exception{
        if(null == factoryDateList){
            factoryDateList = factoryDateDao.findByBeginDateAndEndDate(beginDate, endDate);
        }
        if(null != factoryDateList && 0 < factoryDateList.size()) {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (FactoryDate factoryDate : factoryDateList) {
                if(factoryDate.getFDAY().getTime() == sdf.parse(date).getTime()){
                    if(factoryDate.getFISWORKTIME() == FactoryDate.ISWORK_YES){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
