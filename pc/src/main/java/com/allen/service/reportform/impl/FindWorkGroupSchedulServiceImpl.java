package com.allen.service.reportform.impl;

import com.allen.dao.reportform.FindWorkGroupSchedulDao;
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

    @Override
    public List<WorkGroupForSchedulBean> find(String startDate, String endDate, String wgId, String name, String coreCode) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("wgId", wgId);
        params.put("name", name);
        params.put("coreCode", coreCode);

        List<WorkGroupForSchedulBean> list = new ArrayList<WorkGroupForSchedulBean>();
        List<Map> productList = findWorkGroupSchedulDao.findProduct(params);
        if(null != productList && 0 < productList.size()){
            for(Map map : productList){
                WorkGroupForSchedulBean workGroupForSchedulBean = new WorkGroupForSchedulBean();

                String productName = map.get("product_name").toString();
                String productCode = map.get("product_no").toString();
                String customerNum = map.get("plan_capacity").toString();
                String stock = map.get("stock_num").toString();

                //记录产品信息
                workGroupForSchedulBean.setProductName(productName);
                workGroupForSchedulBean.setProductCode(productCode);
                workGroupForSchedulBean.setCustomerNum(customerNum);
                workGroupForSchedulBean.setStock(stock);

                //循环查询时间
                Date start = DateUtil.getFormatDate(startDate, DateUtil.shortDatePattern);
                Date end = DateUtil.getFormatDate(endDate, DateUtil.shortDatePattern);
                long startTime = start.getTime();
                long endTime = end.getTime();
                long day = (endTime-startTime)/1000/60/60/24;

                if(day > 0){
                    String date = startDate;
                    Map<String, List<ClassGroupForWgSchedulBean>> cgListMap = new LinkedHashMap<String, List<ClassGroupForWgSchedulBean>>();
                    int maxNum = 0;
                    for(int i=0; i<=day; i++){
                        System.out.println(date);
                        params = new HashMap<String, String>();
                        params.put("date", date);
                        params.put("wgId", wgId);
                        params.put("name", productName);
                        params.put("coreCode", coreCode);
                        params.put("productNo", productCode);
                        params.put("planCapacity", customerNum);
                        params.put("stock", stock);
                        List<Map> detailList = findWorkGroupSchedulDao.findProductDetail(params);
                        List<ClassGroupForWgSchedulBean> classGroupForWgSchedulBeanList = null;
                        if(null != detailList && 0 < detailList.size()){
                            classGroupForWgSchedulBeanList = new ArrayList<ClassGroupForWgSchedulBean>(detailList.size());
                            for(Map map2 : detailList) {
                                ClassGroupForWgSchedulBean classGroupForWgSchedulBean = new ClassGroupForWgSchedulBean();
                                classGroupForWgSchedulBean.setCgName(map2.get("work_core_name").toString());
                                classGroupForWgSchedulBean.setHour(map2.get("work_time").toString());
                                classGroupForWgSchedulBean.setNum(map2.get("capacity").toString());
                                classGroupForWgSchedulBean.setWtName(map2.get("work_class_name").toString());
                                classGroupForWgSchedulBean.setTime(map2.get("work_time_start").toString().substring(11, 16)+"-"+map2.get("work_time_end").toString().substring(11, 16));
                                classGroupForWgSchedulBeanList.add(classGroupForWgSchedulBean);
                            }
                            if(maxNum < detailList.size()) {
                                maxNum = detailList.size();
                            }
                        }
                        cgListMap.put(date, classGroupForWgSchedulBeanList);
                        date = DateUtil.afterDay(date);
                    }
                    //cgListMap.put("maxNum", maxNum);
                    workGroupForSchedulBean.setCgListMap(cgListMap);
                    list.add(workGroupForSchedulBean);
                }
            }
        }
        return list;
    }
}
