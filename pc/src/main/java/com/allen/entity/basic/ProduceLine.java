package com.allen.entity.basic;

import javax.persistence.*;
import java.util.Date;

/**
 * 生产线
 * Created by Allen on 2017/2/16.
 */
@Entity
@Table(name = "produce_line")
public class ProduceLine {

    /**
     * 是否公用
     * 0：否
     * 1：是
     */
    private final static int ISPUBLIC_NOT = 0;
    private final static int ISPUBLIC_YES = 1;

    @Id
    @GeneratedValue
    private long id;
    private String code;
    private String name;
    private int isPublic;
    private long useProductId;          //正在被使用得产品id
    private String productIds;
    private String productNames;
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();
    @Transient
    private String isPublicStr;
    @Transient
    private String isUseStr;

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

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getProductNames() {
        return productNames;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public long getUseProductId() {
        return useProductId;
    }

    public void setUseProductId(long useProductId) {
        this.useProductId = useProductId;
    }

    public String getIsPublicStr() {
        switch (this.getIsPublic()){
            case 1:
                this.isPublicStr = "是";
                break;
            case 0:
                this.isPublicStr = "否";
                break;
            default:
                this.isPublicStr = "未知";
                break;
        }
        return isPublicStr;
    }

    public String getIsUseStr() {
        if(0 < this.getUseProductId()){
            this.isUseStr = "是";
        }else{
            this.isUseStr = "否";
        }
        return isUseStr;
    }
}
