package com.allen.service.reportform.impl;

import com.allen.dao.reportform.FindCapacityDao;
import com.allen.entity.pojo.classgroup.ClassGroupForCapacityBean;
import com.allen.entity.pojo.workcore.WorkCoreForCapacityBean;
import com.allen.entity.pojo.workgroup.WorkGroupForCapacityBean;
import com.allen.service.reportform.FindCapacityService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/21.
 */
@Service
public class FindCapacityServiceImpl implements FindCapacityService {

    @Resource
    private FindCapacityDao findCapacityDao;

    @Override
    public List<WorkGroupForCapacityBean> find(Map<String, String> params) throws Exception {
        List<Map> list = findCapacityDao.findByFmaterialIds(params);
        if(null != list && 0 < list.size()){
            List<WorkGroupForCapacityBean> returnList = new ArrayList<WorkGroupForCapacityBean>();
            String beforeWgName = "";
            String beforeWcName = "";
            WorkGroupForCapacityBean workGroup = null;
            WorkCoreForCapacityBean workCore = null;
            List<WorkCoreForCapacityBean> wcList;
            List<ClassGroupForCapacityBean> cgList;
            for(Map map : list){
                String wgName = null == map.get("wgName") ? "" : map.get("wgName").toString();
                String wcName = null == map.get("wcName") ? "" : map.get("wcName").toString();
                String cgName = null == map.get("cgName") ? "" : map.get("cgName").toString();
                String wtName = null == map.get("wtName") ? "" : map.get("wtName").toString();
                String productDate = null == map.get("production_date") ? "" : map.get("production_date").toString();
                String beginTime = null == map.get("begin_time") ? "" : map.get("begin_time").toString();
                String endTime = null == map.get("end_time") ? "" : map.get("end_time").toString();
                String addTime = null == map.get("add_time") ? "" : map.get("add_time").toString();
                String capacity = null == map.get("capacity") ? "" : map.get("capacity").toString();

                ClassGroupForCapacityBean classGroup = new ClassGroupForCapacityBean();
                classGroup.setCgName(cgName);
                classGroup.setWtName(wtName);
                classGroup.setWorkDate(productDate+" "+beginTime+" - "+endTime);
                classGroup.setIsAddWork("0".equals(addTime) ? ClassGroupForCapacityBean.IS_ADDWORK_NOT : ClassGroupForCapacityBean.IS_ADDWORK_YES);
                classGroup.setAddWorkTime(addTime);
                classGroup.setCapacity(capacity);

                if(StringUtil.isEmpty(beforeWgName)){
                    workGroup = new WorkGroupForCapacityBean();
                    workGroup.setWgName(wgName);
                    wcList = new ArrayList<WorkCoreForCapacityBean>();
                    workCore = new WorkCoreForCapacityBean();
                    workCore.setWcName(wcName);
                    cgList = new ArrayList<ClassGroupForCapacityBean>();

                    cgList.add(classGroup);
                    wcList.add(workCore);

                    beforeWgName = wgName;
                    beforeWcName = wcName;

                    returnList.add(workGroup);
                }else {
                    if(!beforeWgName.equals(wgName)){
                        workGroup = new WorkGroupForCapacityBean();
                        workGroup.setWgName(wgName);
                        wcList = new ArrayList<WorkCoreForCapacityBean>();
                        workCore = new WorkCoreForCapacityBean();
                        workCore.setWcName(wcName);
                        cgList = new ArrayList<ClassGroupForCapacityBean>();

                        cgList.add(classGroup);
                        wcList.add(workCore);
                        beforeWgName = wgName;
                        beforeWcName = wcName;

                        returnList.add(workGroup);
                    }else{
                        if(!beforeWcName.equals(wcName)){
                            workCore = new WorkCoreForCapacityBean();
                            workCore.setWcName(wcName);
                            cgList = new ArrayList<ClassGroupForCapacityBean>();

                            cgList.add(classGroup);

                            wcList = workGroup.getWcList();
                            wcList.add(workCore);
                            workGroup.setWcList(wcList);
                        }else{
                            cgList = workCore.getCgList();
                            cgList.add(classGroup);
                            workCore.setCgList(cgList);
                        }
                    }
                }
            }
            return returnList;
        }
        return null;
    }
}
