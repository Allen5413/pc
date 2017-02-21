package com.allen.entity.basic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品信息
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private int selfMade = 0;
    private long type;
    private String creator;
    private Date createTime = new Date();
    private long creatorId;
    private String operator;
    private Date operateTime = new Date();
    private long operatorId = 0;
    @Transient
    private List<ProductSelfUse> productSelfUses = new ArrayList<ProductSelfUse>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public int getSelfMade() {
        return selfMade;
    }

    public void setSelfMade(int selfMade) {
        this.selfMade = selfMade;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public List<ProductSelfUse> getProductSelfUses() {
        return productSelfUses;
    }

    public void setProductSelfUses(List<ProductSelfUse> productSelfUses) {
        this.productSelfUses = productSelfUses;
    }
}
