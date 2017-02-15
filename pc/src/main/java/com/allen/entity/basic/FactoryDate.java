package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 工厂日历
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "factory_date")
public class FactoryDate{

    /**
     * 是否上班
     * 0：否
     * 1：是
     */
    private final static int ISWORK_NOT = 0;
    private final static int ISWORK_YES = 1;

    @Id
    @GeneratedValue
    private long id;
    private String year;
    private String month;
    private String day;
    private int isWork;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getIsWork() {
        return isWork;
    }

    public void setIsWork(int isWork) {
        this.isWork = isWork;
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
