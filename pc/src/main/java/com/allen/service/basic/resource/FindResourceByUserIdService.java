package com.allen.service.basic.resource;

import com.allen.entity.basic.Resource;

import java.util.List;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindResourceByUserIdService {
    public List<Resource> find(long userId)throws Exception;
}
