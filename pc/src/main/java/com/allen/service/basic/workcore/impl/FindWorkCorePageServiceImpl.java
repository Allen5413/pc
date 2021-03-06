package com.allen.service.basic.workcore.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.workcore.FindWorkCoreDao;
import com.allen.service.basic.workcore.FindWorkCorePageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindWorkCorePageServiceImpl implements FindWorkCorePageService {

    @Resource
    private FindWorkCoreDao findWorkCoreDao;

    @Override
    public PageInfo find(PageInfo pageInfo, Map<String, Object> params, Map<String, Boolean> sortMap) throws Exception {
        Long wgId = (Long) params.get("wgc.workGroupId");
        Long plId = (Long) params.get("plc.produceLineId");
        if(null == wgId && null == plId) {
            return findWorkCoreDao.findPage(pageInfo, params, sortMap);
        }
        if(null != wgId && null == plId) {
            return findWorkCoreDao.findPageByWgId(pageInfo, params, sortMap);
        }
        if(null == wgId && null != plId) {
            return findWorkCoreDao.findPageByPlId(pageInfo, params, sortMap);
        }
        if(null != wgId && null != plId) {
            return findWorkCoreDao.findPageByWgIdAndPlId(pageInfo, params, sortMap);
        }
        return null;
    }
}
