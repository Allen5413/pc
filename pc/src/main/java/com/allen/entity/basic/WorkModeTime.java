package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 工作模式中心管理
 * Created by Allen on 2017/2/16 0016.
 */
@Entity
@Table(name = "work_mode_time")
public class WorkModeTime {
    @Id
    @GeneratedValue
    private long id;
    private long workModeId;
    private long workTimeId;
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWorkModeId() {
        return workModeId;
    }

    public void setWorkModeId(long workModeId) {
        this.workModeId = workModeId;
    }

    public long getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(long workTimeId) {
        this.workTimeId = workTimeId;
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
}
