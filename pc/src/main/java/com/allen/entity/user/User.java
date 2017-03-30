package com.allen.entity.user;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Allen on 2016/12/12.
 */
@Entity
@Table(name = "user_l")
public class User {
    public static final int STATE_DELETE = 0;     //删除
    public static final int STATE_ENABLE = 1;     //启用
    public static final int STATE_DISABLE = 2;    //停用

    @Id
    @GeneratedValue
    private Long id;                            //主键
    private String loginName;
    private String pwd;
    private String name;                        //姓名
    private String phone;                       //手机
    private Integer state;                      //用户状态
    private String remark;                      //备注
    private String creator;                     //创建人
    private Date createTime = new Date();       //创建时间
    private String operator;                    //操作人
    private Date operateTime = new Date();      //操作时间
    @Version
    private Integer version;                    //版本号，用于乐观锁
    @Transient
    private String stateStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStateStr() {
        switch (this.getState()){
            case 1:
                this.stateStr = "启用";
                break;
            case 2:
                this.stateStr = "停用";
                break;
            default:
                this.stateStr = "未知";
                break;
        }
        return stateStr;
    }
}
