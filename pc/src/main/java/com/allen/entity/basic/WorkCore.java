package com.allen.entity.basic;

import javax.persistence.*;
import java.util.Date;

/**
 * 工作中心
 * Created by Allen on 2017/2/14 0014.
 */
@Entity
@Table(name = "work_core")
public class WorkCore {

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
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();

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

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
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
}
