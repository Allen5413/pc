package com.allen.entity.pojo.workcore;

import com.allen.entity.pojo.classgroup.ClassGroupForCapacityBean;

import java.util.List;

/**
 * 计算产能排班报表时用的工作中心对象
 * Created by Allen on 2017/3/21.
 */
public class WorkCoreForCapacityBean {
    private long wcId;         //工作中心id
    private String wcName;     //工作中心名称
    private List<ClassGroupForCapacityBean> cgList;    //班组信息集合

    public long getWcId() {
        return wcId;
    }

    public void setWcId(long wcId) {
        this.wcId = wcId;
    }

    public String getWcName() {
        return wcName;
    }

    public void setWcName(String wcName) {
        this.wcName = wcName;
    }

    public List<ClassGroupForCapacityBean> getCgList() {
        return cgList;
    }

    public void setCgList(List<ClassGroupForCapacityBean> cgList) {
        this.cgList = cgList;
    }
}
