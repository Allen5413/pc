package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
