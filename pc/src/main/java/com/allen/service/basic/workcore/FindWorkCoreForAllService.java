package com.allen.service.basic.workcore;

import com.allen.entity.basic.WorkCore;

import java.util.List;

/**
 * Created by Allen on 2017/3/10.
 */
public interface FindWorkCoreForAllService {
    public List<WorkCore> find()throws Exception;
}
