package com.allen.entity.basic;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 班组
 * Created by Allen on 2017/3/4.
 */
@Entity
@Table(name = "class_group")
public class ClassGroup {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();
    @Column(name = "max_produce_time",columnDefinition = "decimal(5,2) default '24'")
    private BigDecimal maxProduceTime;//最大生产时间

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

    public BigDecimal getMaxProduceTime() {
        return maxProduceTime;
    }

    public void setMaxProduceTime(BigDecimal maxProduceTime) {
        this.maxProduceTime = maxProduceTime;
    }
}
