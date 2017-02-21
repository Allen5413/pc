package com.allen.entity.pojo.produceline;

/**
 * 生产线业务bean
 * Created by Allen on 2017/2/21.
 */
public class ProduceLineBean {
    private Long id;
    private String code;
    private String name;
    private Long wcId;

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

    public Long getWcId() {
        return wcId;
    }

    public void setWcId(Long wcId) {
        this.wcId = wcId;
    }
}
