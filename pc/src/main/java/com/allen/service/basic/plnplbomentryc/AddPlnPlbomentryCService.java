package com.allen.service.basic.plnplbomentryc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 包路径：com.allen.service.basic.plnplbomentryc
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-04-09 23:43
 */
public interface AddPlnPlbomentryCService {
    public void add(long fId, Date demandDate, BigDecimal FBASEYIELDQTY,long fBomId,long entryId)throws Exception;
}
