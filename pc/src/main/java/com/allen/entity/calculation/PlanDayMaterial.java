package com.allen.entity.calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 包路径：com.allen.entity.calculation
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-14 22:20
 */
public class PlanDayMaterial {
    private String demandDate;//计划时间
    private BigDecimal useQty;//计划生产量
    private boolean isLastProduction;//是否最后生产
    private long materialId;//产品id
    private long customerId;//客户id
    private List<String> childs = new ArrayList<String>();

    public String getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(String demandDate) {
        this.demandDate = demandDate;
    }

    public BigDecimal getUseQty() {
        return useQty;
    }

    public void setUseQty(BigDecimal useQty) {
        this.useQty = useQty;
    }

    public boolean isLastProduction() {
        return isLastProduction;
    }

    public void setLastProduction(boolean lastProduction) {
        isLastProduction = lastProduction;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void addChild(String child){
        this.childs.add(child);
    }
    public List<String> getChilds() {
        return childs;
    }

    public void setChilds(List<String> childs) {
        this.childs = childs;
    }
}
