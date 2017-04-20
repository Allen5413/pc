package com.allen.service.reportform.impl;

import com.allen.dao.basic.producelineuse.ProduceLineUseDao;
import com.allen.dao.reportform.FindWorkGroupSchedulDao;
import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.pojo.classgroup.ClassGroupForWgSchedulBean;
import com.allen.entity.pojo.workgroup.WorkGroupForSchedulBean;
import com.allen.service.reportform.FindWorkGroupSchedulService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Allen on 2017/4/20.
 */
@Service
public class FindWorkGroupSchedulServiceImpl implements FindWorkGroupSchedulService {

    @Resource
    private FindWorkGroupSchedulDao findWorkGroupSchedulDao;
    @Resource
    private ProduceLineUseDao produceLineUseDao;

    @Override
    public List<WorkGroupForSchedulBean> find(String startDate, String endDate, String wgId) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("wgId", wgId);

        List<WorkGroupForSchedulBean> list = new ArrayList<WorkGroupForSchedulBean>();
        List<Map> productList = findWorkGroupSchedulDao.findProduct(params);
        if(null != productList && 0 < productList.size()){
            for(Map map : productList){
                WorkGroupForSchedulBean workGroupForSchedulBean = new WorkGroupForSchedulBean();

                long productId = Long.parseLong(map.get("product_id").toString());
                String productName = map.get("product_name").toString();
                String productCode = map.get("product_no").toString();
                String customerNum = map.get("plan_total_num").toString();
                String stock = map.get("stock_num").toString();

                //记录产品信息
                workGroupForSchedulBean.setProductId(productId);
                workGroupForSchedulBean.setProductName(productName);
                workGroupForSchedulBean.setProductCode(productCode);
                workGroupForSchedulBean.setCustomerNum(customerNum);
                workGroupForSchedulBean.setStock(stock);

                Map<String, List<ClassGroupForWgSchedulBean>> cgListMap = new LinkedHashMap<String, List<ClassGroupForWgSchedulBean>>();

                String tempDate = startDate;
                //循环计算每天的工作中心时长
                do {
                    List<ClassGroupForWgSchedulBean> cgList = null;
                    List<ProduceLineUse> produceLineUseList = produceLineUseDao.findByProductionDateAndProductId(DateUtil.getFormatDate(tempDate, DateUtil.shortDatePattern), productId);
                    if(null != produceLineUseList && 0 < produceLineUseList.size()){
                        cgList = new ArrayList<ClassGroupForWgSchedulBean>();
                        //从id最小的班次开始计算
                        //得到总的时长
                        //得到最先开始得工作时间
                    }
                    cgListMap.put(tempDate, cgList);
                    tempDate = DateUtil.afterDay(tempDate);
                }while (DateUtil.compareDate(endDate, tempDate) > 0);

                workGroupForSchedulBean.setCgListMap(cgListMap);
            }
        }

        return list;
    }
}
