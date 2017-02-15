package com.allen.service.basic.workcore.impl;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.service.basic.workcore.FindWorkCoreAndWorkGroupIdByWorkGroupIdService;
import com.allen.service.basic.workgroup.FindWorkGroupAndWorkCoreIdByWorkCoreIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过工作组id查询工作中心信息
 * 查询结果的工作组id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
@Service
public class FindWorkCoreAndWorkGroupIdByWorkGroupIdServiceImpl implements FindWorkCoreAndWorkGroupIdByWorkGroupIdService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    public JSONObject find(long workGroupId) throws Exception {
        JSONObject jsonObject = null;
        List<Object[]> list = workCoreDao.findWorkGroupAndWorkGroupIdByWorkGroupId(workGroupId);
        if(null != list && 0 < list.size()){
            jsonObject = new JSONObject();
            List<JSONObject> allList = new ArrayList<JSONObject>(list.size());
            List<JSONObject> withList = new ArrayList<JSONObject>();
            List<JSONObject> notWithList = new ArrayList<JSONObject>();
            for(Object[] objs : list){
                JSONObject json = new JSONObject();
                json.put("id", objs[0]);
                json.put("code", objs[1]);
                json.put("name", objs[2]);
                json.put("wgId", objs[3]);
                allList.add(json);

                if(null != objs[3] && !StringUtil.isEmpty(objs[3].toString())){
                    withList.add(json);
                }
                if(null == objs[3] || StringUtil.isEmpty(objs[3].toString())){
                    notWithList.add(json);
                }
            }
            jsonObject.put("allList", allList);
            jsonObject.put("withList", withList);
            jsonObject.put("notWithList", notWithList);
        }
        return jsonObject;
    }
}
