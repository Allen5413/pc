package com.allen.entity.basic;

import com.allen.util.DateUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工作模式
 * Created by Allen on 2017/2/16 0016.
 */
@Entity
@Table(name = "work_mode")
public class WorkMode {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();
    @Transient
    private List<WorkModeTime> workModeTimeList = new ArrayList<WorkModeTime>();

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

    public List<WorkModeTime> getWorkModeTimeList() {
        return workModeTimeList;
    }

    public void setWorkModeTimeList(List<WorkModeTime> workModeTimeList) {
        this.workModeTimeList = workModeTimeList;
    }
}
