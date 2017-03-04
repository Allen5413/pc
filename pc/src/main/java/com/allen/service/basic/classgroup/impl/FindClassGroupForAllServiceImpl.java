package com.allen.service.basic.classgroup.impl;

import com.allen.dao.basic.classgroup.ClassGroupDao;
import com.allen.entity.basic.ClassGroup;
import com.allen.service.basic.classgroup.FindClassGroupForAllService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Allen on 2017/2/21 0021.
 */
@Service
public class FindClassGroupForAllServiceImpl implements FindClassGroupForAllService {

    @Resource
    private ClassGroupDao classGroupDao;

    @Override
    public List<ClassGroup> find() throws Exception {
        return (List<ClassGroup>) classGroupDao.findAll();
    }
}
