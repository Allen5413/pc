package com.allen.service.basic.workgroup.impl;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.basic.workgroup.WorkGroupDao;
import com.allen.entity.pojo.workgroup.WorkGroupBean;
import com.allen.service.basic.workgroup.FindWorkGroupAndWorkCoreIdByWorkCoreIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过工作中心id查询工作组信息
 * 查询结果的工作id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
@Service
public class FindWorkGroupAndWorkCoreIdByWorkCoreIdServiceImpl implements FindWorkGroupAndWorkCoreIdByWorkCoreIdService {

    @Resource
    private WorkGroupDao workGroupDao;

    @Override
    public JSONObject find(long workCoreId) throws Exception {
        JSONObject jsonObject = null;
        List<Object[]> list = workGroupDao.findWorkGroupAndWorkCoreIdByWorkCoreId(workCoreId);
        if(null != list && 0 < list.size()){
            jsonObject = new JSONObject();
            List<WorkGroupBean> allList = new ArrayList<WorkGroupBean>(list.size());
            List<WorkGroupBean> withList = new ArrayList<WorkGroupBean>();
            List<WorkGroupBean> notWithList = new ArrayList<WorkGroupBean>();
            for(Object[] objs : list){
                WorkGroupBean workGroupBean = new WorkGroupBean();
                workGroupBean.setId(null == objs[0] ? null : Long.parseLong(objs[0].toString()));
                workGroupBean.setCode((String)objs[1]);
                workGroupBean.setName((String)objs[2]);
                workGroupBean.setWcId(null == objs[3] ? null : Long.parseLong(objs[3].toString()));
                allList.add(workGroupBean);

                if(null != objs[3] && !StringUtil.isEmpty(objs[3].toString())){
                    withList.add(workGroupBean);
                }
                if(null == objs[3] || StringUtil.isEmpty(objs[3].toString())){
                    notWithList.add(workGroupBean);
                }
            }
            jsonObject.put("allList", allList);
            jsonObject.put("withList", withList);
            jsonObject.put("notWithList", notWithList);
        }
        return jsonObject;
    }

    @Override
    public List<WorkGroupBean> findWith(long workCoreId) throws Exception {
        JSONObject json = this.find(workCoreId);
        if(null != json){
            List<WorkGroupBean> withList = (List<WorkGroupBean>) json.get("withList");
            return withList;
        }
        return null;
    }

    @Override
    public List<WorkGroupBean> findNotWith(long workCoreId) throws Exception {
        JSONObject json = this.find(workCoreId);
        if(null != json){
            List<WorkGroupBean> notWithList = (List<WorkGroupBean>) json.get("notWithList");
            return notWithList;
        }
        return null;
    }
}
