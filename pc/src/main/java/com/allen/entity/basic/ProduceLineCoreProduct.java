package com.allen.entity.basic;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产线关联工作中心，再关联产品
 * Created by Allen on 2017/2/16.
 */
@Entity
@Table(name = "produce_line_core_product")
public class ProduceLineCoreProduct {
    @Id
    @GeneratedValue
    private long id;
    private long produceLineCoreId;
    private long productId;
    private long workModeId;
    private long unitTimeCapacity;      //单位时间产能  数据库保存为 秒
    private int qualifiedRate;          //合格率
    private int minBatch;               //最小批量 个
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

    public long getProduceLineCoreId() {
        return produceLineCoreId;
    }

    public void setProduceLineCoreId(long produceLineCoreId) {
        this.produceLineCoreId = produceLineCoreId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public int getQualifiedRate() {
        return qualifiedRate;
    }

    public void setQualifiedRate(int qualifiedRate) {
        this.qualifiedRate = qualifiedRate;
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
}