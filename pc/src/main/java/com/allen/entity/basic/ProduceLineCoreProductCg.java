package com.allen.entity.basic;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产线关联工作中心，再关联产品，在关联班组
 * Created by Allen on 2017/2/16.
 */
@Entity
@Table(name = "produce_line_core_product_cg")
public class ProduceLineCoreProductCg {
    @Id
    @GeneratedValue
    private long id;
    private long produceLineCoreProductId;
    private long classGroupId;
    private long workModeId;
    private long unitTimeCapacity;      //单位时间产能  数据库保存为 秒
    private int minBatch;               //最小批量 个
    private int sno;                    //优先级序号
    private String operator;
    private Date operateTime = new Date();
    @Transient
    private int unitTimeCapacityToHour;     //单位时间 小时

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduceLineCoreProductId() {
        return produceLineCoreProductId;
    }

    public void setProduceLineCoreProductId(long produceLineCoreProductId) {
        this.produceLineCoreProductId = produceLineCoreProductId;
    }

    public long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public long getWorkModeId() {
        return workModeId;
    }

    public void setWorkModeId(long workModeId) {
        this.workModeId = workModeId;
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

    public long getUnitTimeCapacity() {
        return unitTimeCapacity;
    }

    public void setUnitTimeCapacity(long unitTimeCapacity) {
        this.unitTimeCapacity = unitTimeCapacity;
    }

    public int getMinBatch() {
        return minBatch;
    }

    public void setMinBatch(int minBatch) {
        this.minBatch = minBatch;
    }

    public int getUnitTimeCapacityToHour() {
        return new BigDecimal(this.getUnitTimeCapacity()).divide(new BigDecimal(3600).setScale(2, BigDecimal.ROUND_UP)).intValue();
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
}
