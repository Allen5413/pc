package com.allen.service.basic.workgroup;

import com.allen.entity.basic.WorkGroup;

import java.util.List;

/**
 * Created by Allen on 2017/2/21 0021.
 */
public interface FindWorkGroupForAllService {
    public List<WorkGroup> find()throws Exception;
}
