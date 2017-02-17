package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 生产线
 * Created by Allen on 2017/2/16.
 */
@Entity
@Table(name = "produce_line_core")
public class ProduceLineCore {
    @Id
    @GeneratedValue
    private long id;
    private long produceLineId;
    private long workCoreId;
    private String operator;
    private Date operateTime = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduceLineId() {
        return produceLineId;
    }

    public void setProduceLineId(long produceLineId) {
        this.produceLineId = produceLineId;
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
