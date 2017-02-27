package com.allen.entity.pojo.product;

/**
 * 产品信息业务bean
 * Created by Allen on 2017/2/27 0027.
 */
public class ProductBean {
    private long id;
    private String code;
    private String name;
    private int selfMade;
    private String tName;
    private long workModelId;

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

    public int getSelfMade() {
        return selfMade;
    }

    public void setSelfMade(int selfMade) {
        this.selfMade = selfMade;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public long getWorkModelId() {
        return workModelId;
    }

    public void setWorkModelId(long workModelId) {
        this.workModelId = workModelId;
    }
}
