package com.allen.entity.basic;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 包路径：com.allen.entity.basic
 * 功能说明：生产计划信息
 * 创建人： ly
 * 创建时间: 2017-03-07 20:40
 */
@Entity
@Table(name="production_plan")
public class ProductionPlan implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private long productId;//产品id
    private String productNo;//产品编号
    private String productName;//产品名称
    private String productType;//产品类型
    private Date productionDate;//生产日期
    private BigDecimal demandNum;//需求量
    private BigDecimal productionNum;//生产量
    private BigDecimal planNum;//计划量
    private BigDecimal stockNum;//库存量
    private BigDecimal grossNum;//客户需求量
    private BigDecimal planTotalNum;//计划总量
    @Transient
    private Map<String,HashMap<String,Object>> plans = new HashMap<String, HashMap<String, Object>>();
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public BigDecimal getDemandNum() {
        return demandNum;
    }

    public void setDemandNum(BigDecimal demandNum) {
        this.demandNum = demandNum;
    }

    public BigDecimal getProductionNum() {
        return productionNum;
    }

    public void setProductionNum(BigDecimal productionNum) {
        this.productionNum = productionNum;
    }

    public BigDecimal getPlanNum() {
        return planNum;
    }

    public void setPlanNum(BigDecimal planNum) {
        this.planNum = planNum;
    }

    public BigDecimal getStockNum() {
        return stockNum;
    }

    public void setStockNum(BigDecimal stockNum) {
        this.stockNum = stockNum;
    }

    public BigDecimal getGrossNum() {
        return grossNum;
    }

    public void setGrossNum(BigDecimal grossNum) {
        this.grossNum = grossNum;
    }

    public BigDecimal getPlanTotalNum() {
        return planTotalNum;
    }

    public void setPlanTotalNum(BigDecimal planTotalNum) {
        this.planTotalNum = planTotalNum;
    }

    public Map<String, HashMap<String, Object>> getPlans() {
        return plans;
    }

    public void setPlans(Map<String, HashMap<String, Object>> plans) {
        this.plans = plans;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
