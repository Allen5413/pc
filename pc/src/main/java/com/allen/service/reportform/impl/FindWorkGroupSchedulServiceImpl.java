package com.allen.service.reportform.impl;

import com.allen.dao.reportform.FindWorkGroupSchedulDao;
import com.allen.entity.pojo.reportform.WorkGroupSchedulBean;
import com.allen.service.reportform.FindWorkGroupSchedulService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/4/20.
 */
@Service
public class FindWorkGroupSchedulServiceImpl implements FindWorkGroupSchedulService {

    @Resource
    private FindWorkGroupSchedulDao findWorkGroupSchedulDao;

    @Override
    public List<WorkGroupSchedulBean> find(String startDate, String endDate, String wgId) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("wgId", wgId);

        List<WorkGroupSchedulBean> list = new ArrayList<WorkGroupSchedulBean>();
        List<Map> productList = findWorkGroupSchedulDao.findProduct(params);
        if(null != productList && 0 < productList.size()){
            for(Map map : productList){
                long productId = Long.parseLong(map.get("product_id").toString());
                String productName = map.get("product_name").toString();
                String productCode = map.get("product_no").toString();
                String customerNum = map.get("plan_total_num").toString();
                String stock = map.get("stock_num").toString();

            }
        }

        return list;
    }
}
