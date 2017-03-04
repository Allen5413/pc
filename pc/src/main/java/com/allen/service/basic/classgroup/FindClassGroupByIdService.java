package com.allen.service.basic.classgroup;

import com.allen.entity.basic.ClassGroup;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindClassGroupByIdService {
    public ClassGroup find(long id)throws Exception;
}
