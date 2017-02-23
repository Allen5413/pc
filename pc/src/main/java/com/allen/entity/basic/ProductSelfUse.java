package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品自制件用量信息
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "product_self_use")
public class ProductSelfUse {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private long productId;
    private long selfProductId;
    private BigDecimal quantity;
    private String creator;
    private BigDecimal ahead = new BigDecimal(0);
    private int level;
    private Date createTime = new Date();
    private long creatorId;
    private String operator;
    private Date operateTime = new Date();
    private long operatorId = 0;

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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSelfProductId() {
        return selfProductId;
    }

    public void setSelfProductId(long selfProductId) {
        this.selfProductId = selfProductId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public BigDecimal getAhead() {
        return ahead;
    }

    public void setAhead(BigDecimal ahead) {
        this.ahead = ahead;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
