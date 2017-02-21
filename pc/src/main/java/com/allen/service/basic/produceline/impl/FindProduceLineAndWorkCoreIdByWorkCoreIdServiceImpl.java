package com.allen.service.basic.produceline.impl;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.basic.produceline.ProduceLineDao;
import com.allen.entity.pojo.produceline.ProduceLineBean;
import com.allen.service.basic.produceline.FindProduceLineAndWorkCoreIdByWorkCoreIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过工作中心id查询生产线信息
 * 查询结果的工作中心id字段，如果有值说明有关联，没有值说明没有关联
 * Created by Allen on 2017/2/15.
 */
@Service
public class FindProduceLineAndWorkCoreIdByWorkCoreIdServiceImpl implements FindProduceLineAndWorkCoreIdByWorkCoreIdService {

    @Resource
    private ProduceLineDao produceLineDao;

    @Override
    public JSONObject find(long workCoreId) throws Exception {
        JSONObject jsonObject = null;
        List<Object[]> list = produceLineDao.findProduceLineAndWorkCoreIdByWorkCoreId(workCoreId);
        if(null != list && 0 < list.size()){
            jsonObject = new JSONObject();
            List<ProduceLineBean> allList = new ArrayList<ProduceLineBean>(list.size());
            List<ProduceLineBean> withList = new ArrayList<ProduceLineBean>();
            List<ProduceLineBean> notWithList = new ArrayList<ProduceLineBean>();
            for(Object[] objs : list){
                ProduceLineBean produceLineBean = new ProduceLineBean();
                produceLineBean.setId(null == objs[0] ? null : Long.parseLong(objs[0].toString()));
                produceLineBean.setCode((String) objs[1]);
                produceLineBean.setName((String) objs[2]);
                produceLineBean.setWcId(null == objs[3] ? null : Long.parseLong(objs[3].toString()));
                allList.add(produceLineBean);

                if(null != objs[3] && !StringUtil.isEmpty(objs[3].toString())){
                    withList.add(produceLineBean);
                }
                if(null == objs[3] || StringUtil.isEmpty(objs[3].toString())){
                    notWithList.add(produceLineBean);
                }
            }
            jsonObject.put("allList", allList);
            jsonObject.put("withList", withList);
            jsonObject.put("notWithList", notWithList);
        }
        return jsonObject;
    }

    @Override
    public List<ProduceLineBean> findWith(long workCoreId) throws Exception {
        JSONObject json = this.find(workCoreId);
        if(null != json){
            List<ProduceLineBean> withList = (List<ProduceLineBean>) json.get("withList");
            return withList;
        }
        return null;
    }

    @Override
    public List<ProduceLineBean> findNotWith(long workCoreId) throws Exception {
        JSONObject json = this.find(workCoreId);
        if(null != json){
            List<ProduceLineBean> notWithList = (List<ProduceLineBean>) json.get("notWithList");
            return notWithList;
        }
        return null;
    }
}
