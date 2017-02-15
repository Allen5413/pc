package com.allen.service.basic.workgroup;

import com.allen.entity.basic.WorkGroup;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindWorkGroupByIdService {
    public WorkGroup find(long id)throws Exception;
}
