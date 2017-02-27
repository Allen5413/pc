package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private long workModelId;
    private String operator;
    private Date operateTime = new Date();

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

    public long getWorkModelId() {
        return workModelId;
    }

    public void setWorkModelId(long workModelId) {
        this.workModelId = workModelId;
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
