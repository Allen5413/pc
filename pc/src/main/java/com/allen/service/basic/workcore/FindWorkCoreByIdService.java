package com.allen.service.basic.workcore;

import com.allen.entity.basic.WorkCore;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindWorkCoreByIdService {
    public WorkCore find(long id)throws Exception;
}
