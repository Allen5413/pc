package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 工作组关联工作中心
 * Created by Allen on 2017/2/14 0014.
 */
@Entity
@Table(name = "work_group_core")
public class WorkGroupCore {
    @Id
    @GeneratedValue
    private long id;
    private long workGroupId;
    private long workCoreId;
    private String operator;
    private Date operateTime = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWorkGroupId() {
        return workGroupId;
    }

    public void setWorkGroupId(long workGroupId) {
        this.workGroupId = workGroupId;
    }

    public long getWorkCoreId() {
        return workCoreId;
    }

    public void setWorkCoreId(long workCoreId) {
        this.workCoreId = workCoreId;
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
