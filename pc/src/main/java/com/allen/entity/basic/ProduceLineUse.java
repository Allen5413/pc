package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 包路径：com.allen.entity.basic
 * 功能说明：生产线使用情况
 * 创建人： ly
 * 创建时间: 2017-03-07 20:40
 */
@Entity
@Table(name="produce_line_use")
public class ProduceLineUse implements Serializable{
    public static int FULL= 1;
    public static int UNFULL = 0;
    @Id
    @GeneratedValue
    private long cId;
    private long produceLineId;//生产线id
    private long productId;//产品id
    private Date productionDate;//生产日期
    private long workCoreId;//工作中心id
    private long workTimeId;//班次id
    private BigDecimal addTime;//加班时间
    private long workTeamId;//班组id
    private BigDecimal capacity;//实际产能
    private BigDecimal planQuantity;//生产计划量
    private BigDecimal balanceCapacity;//剩余产能
    private BigDecimal balanceTime;//剩余时间
    private int isFull;//生产线是否已经排满
    private long customerId;//客户id
    private BigDecimal workTime;//工作时间
    private BigDecimal workStart;//工作开始时间
    public long getcId() {
        return cId;
    }

    public void setcId(long cId) {
        this.cId = cId;
    }

    public long getProduceLineId() {
        return produceLineId;
    }

    public void setProduceLineId(long produceLineId) {
        this.produceLineId = produceLineId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public long getWorkCoreId() {
        return workCoreId;
    }

    public void setWorkCoreId(long workCoreId) {
        this.workCoreId = workCoreId;
    }

    public long getWorkTimeId() {
        return workTimeId;
    }

    public void setWorkTimeId(long workTimeId) {
        this.workTimeId = workTimeId;
    }

    public BigDecimal getAddTime() {
        return addTime;
    }

    public void setAddTime(BigDecimal addTime) {
        this.addTime = addTime;
    }

    public long getWorkTeamId() {
        return workTeamId;
    }

    public void setWorkTeamId(long workTeamId) {
        this.workTeamId = workTeamId;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public int getIsFull() {
        return isFull;
    }

    public void setIsFull(int isFull) {
        this.isFull = isFull;
    }
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBalanceCapacity() {
        return balanceCapacity;
    }

    public void setBalanceCapacity(BigDecimal balanceCapacity) {
        this.balanceCapacity = balanceCapacity;
    }

    public BigDecimal getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(BigDecimal balanceTime) {
        this.balanceTime = balanceTime;
    }

    public BigDecimal getWorkTime() {
        return workTime;
    }

    public void setWorkTime(BigDecimal workTime) {
        this.workTime = workTime;
    }

    public BigDecimal getWorkStart() {
        return workStart;
    }

    public void setWorkStart(BigDecimal workStart) {
        this.workStart = workStart;
    }
}
