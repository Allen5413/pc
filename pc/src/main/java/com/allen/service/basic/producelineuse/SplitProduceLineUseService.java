package com.allen.service.basic.producelineuse;

import java.util.Date;

/**
 * 包路径：com.allen.service.basic.producelineuse
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-06-01 23:02
 */
public interface SplitProduceLineUseService {
    /**
     * 功能:拆分排班信息
     * @param start
     * @param end
     */
    public void splitProduceLineUserService(Date start,Date end) throws Exception;
}
