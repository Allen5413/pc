package com.allen.entity.basic;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Allen on 2016/12/12.
 */
@Entity
@Table(name = "resource")
public class Resource implements Serializable{
    public static final int MENU = 1;     //删除
    public static final int BUTTON = 2;     //启用

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String remark;
    private String url;
    private long menuId;
    private String creator;
    private Date createTime = new Date();
    private String operator;
    private Date operateTime = new Date();
    private long parentId=0;//上级资源id
    private int isButton = MENU;//1菜单 2按钮
    private String buttonCode="";//按钮编码
    private int sno;//排序号
    @Version
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getIsButton() {
        return isButton;
    }

    public void setIsButton(int isButton) {
        this.isButton = isButton;
    }

    public String getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }
}
