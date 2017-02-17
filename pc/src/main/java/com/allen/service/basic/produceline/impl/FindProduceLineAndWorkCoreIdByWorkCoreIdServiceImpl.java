package com.allen.service.basic.produceline.impl;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.basic.produceline.ProduceLineDao;
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
            List<JSONObject> allList = new ArrayList<JSONObject>(list.size());
            List<JSONObject> withList = new ArrayList<JSONObject>();
            List<JSONObject> notWithList = new ArrayList<JSONObject>();
            for(Object[] objs : list){
                JSONObject json = new JSONObject();
                json.put("id", objs[0]);
                json.put("code", objs[1]);
                json.put("name", objs[2]);
                json.put("wcId", objs[3]);
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
