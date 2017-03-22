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
    private BigDecimal useQtyStock;//实际计划生产量（计算库存后的数量）
    private BigDecimal capacity;//实际生产量
    private BigDecimal balanceCapacity;//剩余产能
    private boolean isFullWork;//是否已经满负荷生产
    private boolean isLastProduction;//是否最后生产
    private long materialId;//产品id
    private long customerId;//客户id
    private String productNo;//产品编号
    private String productName;//产品名称
    private String productType;//产品类型
    private List<String> childs = new ArrayList<String>();//记录子产品的

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
    //获取当前产品的下级产品
    public List<String[]> getSelfChilds(){
        List<String[]> selfChids = null;
        if(childs!=null&&childs.size()>0){
            selfChids = new ArrayList<String[]>();
            for(String str:childs){
                String[] materialAttr = str.split(",");
                //产品是自制半成品 产成品
                if("239".equals(materialAttr[2])||"241".equals(materialAttr[2])){
                    selfChids.add(materialAttr);
                }
            }
        }
        return  selfChids;
    }
    public void setChilds(List<String> childs) {
        this.childs = childs;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getUseQtyStock() {
        return useQtyStock;
    }

    public void setUseQtyStock(BigDecimal useQtyStock) {
        this.useQtyStock = useQtyStock;
    }

    public boolean isFullWork() {
        return isFullWork;
    }

    public void setFullWork(boolean fullWork) {
        isFullWork = fullWork;
    }

    public BigDecimal getBalanceCapacity() {
        return balanceCapacity;
    }

    public void setBalanceCapacity(BigDecimal balanceCapacity) {
        this.balanceCapacity = balanceCapacity;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
