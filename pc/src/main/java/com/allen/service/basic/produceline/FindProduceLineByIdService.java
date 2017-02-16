package com.allen.service.basic.produceline;

import com.allen.entity.basic.ProduceLine;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindProduceLineByIdService {
    public ProduceLine find(long id)throws Exception;
}
