package com.allen.entity.basic;

import com.allen.util.DateUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 排班信息表
 * Created by Allen on 2017/3/4.
 */
@Entity
@Table(name = "product_scheduling")
public class ProductScheduling {
    @Id
    @GeneratedValue
    private long id;
    private String productName;//产品名称
    private String productNo;//产品编号
    private Date productionDate;//生产时间
    private BigDecimal capacity;//生产数量
    private BigDecimal workTime;//工作时长
    private Date workTimeStart;//工作开始时间
    private Date workTimeEnd;//工作结束时间
    private String workClassName;//班次名称
    private String workCoreName;//工作中心名称
    private BigDecimal planCapacity;//客户需求量
    private BigDecimal stockNum;//库存量
    private String workClassCode;//班次编码
    private String workCoreCode;//工作中心编码
    private String procName;//工序名称
    private String procCode;//工序编码

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getWorkTime() {
        return workTime;
    }

    public void setWorkTime(BigDecimal workTime) {
        this.workTime = workTime;
    }

    public Date getWorkTimeStart() {
        return workTimeStart;
    }

    public void setWorkTimeStart(Date workTimeStart) {
        this.workTimeStart = workTimeStart;
    }

    public Date getWorkTimeEnd() {
        return workTimeEnd;
    }

    public void setWorkTimeEnd(Date workTimeEnd) {
        this.workTimeEnd = workTimeEnd;
    }

    public String getWorkClassName() {
        return workClassName;
    }

    public void setWorkClassName(String workClassName) {
        this.workClassName = workClassName;
    }

    public String getWorkCoreName() {
        return workCoreName;
    }

    public void setWorkCoreName(String workCoreName) {
        this.workCoreName = workCoreName;
    }

    public BigDecimal getPlanCapacity() {
        return planCapacity;
    }

    public void setPlanCapacity(BigDecimal planCapacity) {
        this.planCapacity = planCapacity;
    }

    public BigDecimal getStockNum() {
        return stockNum;
    }

    public void setStockNum(BigDecimal stockNum) {
        this.stockNum = stockNum;
    }

    public String getWorkClassCode() {
        return workClassCode;
    }

    public void setWorkClassCode(String workClassCode) {
        this.workClassCode = workClassCode;
    }

    public String getWorkCoreCode() {
        return workCoreCode;
    }

    public void setWorkCoreCode(String workCoreCode) {
        this.workCoreCode = workCoreCode;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public static ProductScheduling mapToProductScheduling(Map<String,Object> params){
        ProductScheduling productScheduling = new ProductScheduling();
        productScheduling.setCapacity(new BigDecimal(params.get("newCapacity").toString()));
        productScheduling.setPlanCapacity(new BigDecimal(params.get("plan_quantity").toString()));
        productScheduling.setProductName(params.get("FNAME").toString());
        productScheduling.setProductNo(params.get("FNUMBER").toString());
        productScheduling.setWorkCoreName(params.get("cgName").toString());
        productScheduling.setWorkClassName(params.get("wtName").toString());
        productScheduling.setProductionDate(DateUtil.getFormatDate(params.get("production_date").toString(),
                DateUtil.shortDatePattern));
        productScheduling.setWorkTime(new BigDecimal(params.get("workTime").toString()));
        productScheduling.setWorkTimeStart(DateUtil.getFormatDate(params.get("start").toString(),DateUtil.longDatePattern));
        productScheduling.setWorkTimeEnd(DateUtil.getFormatDate(params.get("end").toString(),DateUtil.longDatePattern));
        productScheduling.setStockNum(new BigDecimal(0));
        productScheduling.setWorkClassCode(params.get("wtCode").toString());
        productScheduling.setWorkCoreCode(params.get("cgCode").toString());
        productScheduling.setProcCode(params.get("wCode").toString());
        productScheduling.setProcName(params.get("wName").toString());
        return productScheduling;
    }
}
