package com.allen.service.basic.plnplbomentry;

import com.allen.entity.basic.PlnPlbomentry;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Allen on 2017/4/5 0005.
 */
public interface AddPlnPlbomentryService {
    public PlnPlbomentry add(long fId, Date demandDate, BigDecimal FBASEYIELDQTY, long fBomId,long fMaterialId)throws Exception;
}
