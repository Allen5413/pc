package com.allen.entity.pojo.classgroup;

/**
 * 计算产能排班报表时用的班组对象
 * Created by Allen on 2017/3/21.
 */
public class ClassGroupForCapacityBean {
    /**
     * 是否加班
     * 0：否；1：是
     */
    public static final int IS_ADDWORK_NOT = 0;
    public static final int IS_ADDWORK_YES = 1;

    private long cgId;              //班组id
    private String cgName;          //班组名称
    private long wtId;              //班次id
    private String wtName;          //班次名称
    private String workDate;        //工作时间
    private int isAddWork;          //是否加班
    private String addWorkTime;     //加班时间
    private String capacity;        //产能

    public long getCgId() {
        return cgId;
    }

    public void setCgId(long cgId) {
        this.cgId = cgId;
    }

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public long getWtId() {
        return wtId;
    }

    public void setWtId(long wtId) {
        this.wtId = wtId;
    }

    public String getWtName() {
        return wtName;
    }

    public void setWtName(String wtName) {
        this.wtName = wtName;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public int getIsAddWork() {
        return isAddWork;
    }

    public void setIsAddWork(int isAddWork) {
        this.isAddWork = isAddWork;
    }

    public String getAddWorkTime() {
        return addWorkTime;
    }

    public void setAddWorkTime(String addWorkTime) {
        this.addWorkTime = addWorkTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
