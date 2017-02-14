package com.allen.service.basic.resource;

import com.allen.entity.basic.Resource;

import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
public interface EditResourceService {
    public void edit(Resource resource, List<Resource> buttons)throws Exception;
}
