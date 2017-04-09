package com.allen.service.basic.plnplbomentry;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Allen on 2017/4/5 0005.
 */
public interface AddPlnPlbomentryService {
    public void add(long fId, Date demandDate, BigDecimal FBASEYIELDQTY,long fBomId)throws Exception;
}
