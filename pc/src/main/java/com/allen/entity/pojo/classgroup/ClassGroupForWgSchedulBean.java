package com.allen.entity.pojo.classgroup;

/**
 * 计算工作组排班报表时用的班组对象
 * Created by Allen on 2017/4/20 0020.
 */
public class ClassGroupForWgSchedulBean {
    private String num;         //生产数量
    private String cgName;      //工作中心
    private String wtName;      //班次
    private String time;        //时间
    private String hour;        //时长

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public String getWtName() {
        return wtName;
    }

    public void setWtName(String wtName) {
        this.wtName = wtName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
