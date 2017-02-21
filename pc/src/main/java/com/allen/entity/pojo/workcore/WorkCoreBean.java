package com.allen.entity.pojo.workcore;

/**
 * 工作中心业务bean
 * Created by Allen on 2017/2/21.
 */
public class WorkCoreBean {
    private Long id;            //中心id
    private String code;        //中心code
    private String name;        //中心名称
    private Long wgId;          //工作组id
    private Long plId;          //生产线id

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

    public Long getWgId() {
        return wgId;
    }

    public void setWgId(Long wgId) {
        this.wgId = wgId;
    }

    public Long getPlId() {
        return plId;
    }

    public void setPlId(Long plId) {
        this.plId = plId;
    }
}
