package com.allen.entity.pojo.workgroup;

import com.allen.entity.pojo.workcore.WorkCoreForCapacityBean;

import java.util.List;

/**
 * 计算产能排班报表时用的工作组对象
 * Created by Allen on 2017/3/21.
 */
public class WorkGroupForCapacityBean {
    private long wgId;          //工作组id
    private String wgName;      //工作组名称
    private List<WorkCoreForCapacityBean> wcList;  //工作中心信息集合

    public long getWgId() {
        return wgId;
    }

    public void setWgId(long wgId) {
        this.wgId = wgId;
    }

    public String getWgName() {
        return wgName;
    }

    public void setWgName(String wgName) {
        this.wgName = wgName;
    }

    public List<WorkCoreForCapacityBean> getWcList() {
        return wcList;
    }

    public void setWcList(List<WorkCoreForCapacityBean> wcList) {
        this.wcList = wcList;
    }
}
