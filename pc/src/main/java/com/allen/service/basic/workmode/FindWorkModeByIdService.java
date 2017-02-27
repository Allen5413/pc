package com.allen.service.basic.workmode;

import com.allen.entity.basic.WorkMode;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindWorkModeByIdService {
    public WorkMode find(long id)throws Exception;
}
