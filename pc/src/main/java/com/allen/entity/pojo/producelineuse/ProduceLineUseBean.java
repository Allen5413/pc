package com.allen.entity.pojo.producelineuse;

import java.math.BigDecimal;

/**
 * 包路径：com.allen.entity.pojo.producelineuse
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-18 23:05
 */
public class ProduceLineUseBean {
    private BigDecimal balanceCapacity = new BigDecimal(0);//生产中心剩
    private boolean isFullWork = false;
    private BigDecimal addWorkTime  = new BigDecimal(0);
    private Long lastFMaterialId = null;//生产中心上次生产产品
    private BigDecimal balanceWorkTime = new BigDecimal(0);//生产中心正常生产上次剩余时间
    private BigDecimal workTotalTime = new BigDecimal(0);//班组工作总时间

    public BigDecimal getBalanceCapacity() {
        return balanceCapacity;
    }

    public void setBalanceCapacity(BigDecimal balanceCapacity) {
        this.balanceCapacity = balanceCapacity;
    }

    public boolean isFullWork() {
        return isFullWork;
    }

    public void setFullWork(boolean fullWork) {
        isFullWork = fullWork;
    }

    public BigDecimal getAddWorkTime() {
        return addWorkTime;
    }

    public void setAddWorkTime(BigDecimal addWorkTime) {
        this.addWorkTime = addWorkTime;
    }

    public Long getLastFMaterialId() {
        return lastFMaterialId;
    }

    public void setLastFMaterialId(Long lstFMaterialId) {
        this.lastFMaterialId = lstFMaterialId;
    }


    public BigDecimal getBalanceWorkTime() {
        return balanceWorkTime;
    }

    public void setBalanceWorkTime(BigDecimal balanceWorkTime) {
        this.balanceWorkTime = balanceWorkTime;
    }

    public BigDecimal getWorkTotalTime() {
        return workTotalTime;
    }

    public void setWorkTotalTime(BigDecimal workTotalTime) {
        this.workTotalTime = workTotalTime;
    }
}
