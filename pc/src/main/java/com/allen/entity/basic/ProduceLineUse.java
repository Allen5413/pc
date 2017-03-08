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
    public static int FLAG = 1;
    public static int UNFLAG = 0;
    @Id
    @GeneratedValue
    private long cId;
    private long produceLineId;//生产线id
    private long productId;//产品id
    private Date productionDate;//生产日期
    private long workCoreId;//工作中心id
    private long workTimeId;//班次id
    private long addTime;//加班时间
    private long workTeamId;//班组id
    private BigDecimal capacity;//实际产能
    private BigDecimal planQuantity;//生产计划量
    private int isFull;//生产线是否已经排满
    private int flag;//同一生产线最晚工作为1其他为0

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

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
