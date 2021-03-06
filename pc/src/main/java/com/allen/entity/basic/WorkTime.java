package com.allen.entity.basic;

import com.allen.util.DateUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 班次
 * Created by Allen on 2017/2/16 0016.
 */
@Entity
@Table(name = "work_time")
public class WorkTime {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private Timestamp beginTime; //开始时间 到分
    private Timestamp endTime;   //结束时间 到分
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();
    private int sno;//班次顺序号
    @Transient
    private String beginTimeStr;
    @Transient
    private String endTimeStr;
    @Transient
    private long timeSub;   //结束时间-开始时间，得到毫秒值
    @Transient
    private String timeSubStr;  //结束时间-开始时间，算出单班时间用于页面显示

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getBeginTimeStr() {
        return DateUtil.getFormattedString(this.getBeginTime(), "HH:mm");
    }

    public String getEndTimeStr() {
        return DateUtil.getFormattedString(this.getEndTime(), "HH:mm");
    }

    public long getTimeSub() {
        return this.getEndTime().getTime() - this.getBeginTime().getTime();
    }

    public String getTimeSubStr() {
        return DateUtil.subtractDateTime(this.getEndTime(), this.getBeginTime(), "hour");
    }
}
