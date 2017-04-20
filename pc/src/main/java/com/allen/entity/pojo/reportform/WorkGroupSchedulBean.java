package com.allen.entity.pojo.reportform;

import java.util.List;

/**
 * 工作组排班表
 * Created by Allen on 2017/4/20.
 */
public class WorkGroupSchedulBean {
    private long productId;                     //产品id
    private String productName;                 //产品名称
    private String productCode;                 //产品编码
    private String customerNum;                 //客户需求
    private String stock;                       //初期库存
    private List<ClassGroupBean> cgList;        //工作中心信息

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

    public List<ClassGroupBean> getCgList() {
        return cgList;
    }

    public void setCgList(List<ClassGroupBean> cgList) {
        this.cgList = cgList;
    }

    class ClassGroupBean{
        private String num;         //生产数量
        private String cgName;      //工作中心
        private String wtName;      //班次
        private String time;        //时间
        private String hour;        //时长

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCgName() {
            return cgName;
        }

        public void setCgName(String cgName) {
            this.cgName = cgName;
        }

        public String getWtName() {
            return wtName;
        }

        public void setWtName(String wtName) {
            this.wtName = wtName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }
    }
}
