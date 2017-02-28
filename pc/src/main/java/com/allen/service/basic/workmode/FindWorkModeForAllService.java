package com.allen.service.basic.workmode;

import com.allen.entity.basic.WorkMode;

import java.util.List;

/**
 * Created by Allen on 2017/2/28.
 */
public interface FindWorkModeForAllService {
    public List<WorkMode> find()throws Exception;
}
