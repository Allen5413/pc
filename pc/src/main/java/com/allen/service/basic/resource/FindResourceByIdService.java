package com.allen.service.basic.resource;

import com.allen.entity.basic.Resource;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindResourceByIdService {
    public Resource find(long id)throws Exception;
}
