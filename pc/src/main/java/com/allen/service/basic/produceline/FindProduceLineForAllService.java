package com.allen.service.basic.produceline;

import com.allen.entity.basic.ProduceLine;

import java.util.List;

/**
 * Created by Allen on 2017/2/21 0021.
 */
public interface FindProduceLineForAllService {
    public List<ProduceLine> find()throws Exception;
}
