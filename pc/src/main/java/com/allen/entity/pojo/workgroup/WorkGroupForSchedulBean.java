package com.allen.entity.pojo.workgroup;

import com.allen.entity.pojo.classgroup.ClassGroupForWgSchedulBean;

import java.util.List;
import java.util.Map;

/**
 * 计算工作组排班报表时用的工作组对象
 * Created by Allen on 2017/4/20 0020.
 */
public class WorkGroupForSchedulBean {
    private long productId;                     //产品id
    private String productName;                 //产品名称
    private String productCode;                 //产品编码
    private String customerNum;                 //客户需求
    private String stock;                       //初期库存
    private Map<String, List<ClassGroupForWgSchedulBean>> cgListMap;        //工作中心信息

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Map<String, List<ClassGroupForWgSchedulBean>> getCgListMap() {
        return cgListMap;
    }

    public void setCgListMap(Map<String, List<ClassGroupForWgSchedulBean>> cgListMap) {
        this.cgListMap = cgListMap;
    }
}
