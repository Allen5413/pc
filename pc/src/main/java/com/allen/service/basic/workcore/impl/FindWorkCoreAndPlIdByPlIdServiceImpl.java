package com.allen.service.basic.workcore.impl;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.basic.workcore.WorkCoreDao;
import com.allen.entity.pojo.workcore.WorkCoreBean;
import com.allen.service.basic.workcore.FindWorkCoreAndPlIdByPlIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过生产线id查询工作中心信息
 * 查询结果的工作组id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
@Service
public class FindWorkCoreAndPlIdByPlIdServiceImpl implements FindWorkCoreAndPlIdByPlIdService {

    @Resource
    private WorkCoreDao workCoreDao;

    @Override
    public JSONObject find(long plId) throws Exception {
        JSONObject jsonObject = null;
        List<Object[]> list = workCoreDao.findWorkCoreAndProduceLineIdByProduceLineId(plId);
        if(null != list && 0 < list.size()){
            jsonObject = new JSONObject();
            List<WorkCoreBean> allList = new ArrayList<WorkCoreBean>(list.size());
            List<WorkCoreBean> withList = new ArrayList<WorkCoreBean>();
            List<WorkCoreBean> notWithList = new ArrayList<WorkCoreBean>();
            for(Object[] objs : list){
                WorkCoreBean workCoreBean = new WorkCoreBean();
                workCoreBean.setId(null == objs[0] ? null : Long.parseLong(objs[0].toString()));
                workCoreBean.setCode((String) objs[1]);
                workCoreBean.setName((String) objs[2]);
                workCoreBean.setPlId(null == objs[3] ? null : Long.parseLong(objs[3].toString()));
                workCoreBean.setPlcId(null == objs[4] ? null : Long.parseLong(objs[4].toString()));
                workCoreBean.setSno(null == objs[5] ? null : Integer.parseInt(objs[5].toString()));
                allList.add(workCoreBean);

                if(null != objs[3] && !StringUtil.isEmpty(objs[3].toString())){
                    withList.add(workCoreBean);
                }
                if(null == objs[3] || StringUtil.isEmpty(objs[3].toString())){
                    notWithList.add(workCoreBean);
                }
            }
            jsonObject.put("allList", allList);
            jsonObject.put("withList", withList);
            jsonObject.put("notWithList", notWithList);
        }
        return jsonObject;
    }

    @Override
    public List<WorkCoreBean> findWith(long plId) throws Exception {
        JSONObject json = this.find(plId);
        if(null != json){
            List<WorkCoreBean> withList = (List<WorkCoreBean>) json.get("withList");
            return withList;
        }
        return null;
    }

    @Override
    public List<WorkCoreBean> findNotWith(long plId) throws Exception {
        JSONObject json = this.find(plId);
        if(null != json){
            List<WorkCoreBean> notWithList = (List<WorkCoreBean>) json.get("notWithList");
            return notWithList;
        }
        return null;
    }
}
