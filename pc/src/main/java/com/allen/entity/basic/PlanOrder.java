package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 包路径：com.allen.entity.basic
 * 功能说明：生产计划信息
 * 创建人： ly
 * 创建时间: 2017-03-04 22:34
 */
@Entity
@Table(name="t_pln_planorder")
public class PlanOrder implements Serializable {
    @Id
    private long FID;
    private String FBILLNO;//单据编号
    private String FDOCUMENTSTATUS;//单据状态 A为创建
    private BigDecimal FFIRMQTY;//确认订单量
    private String FRELEASETYPE;//投放状态  1为生产订单
    private long FMATERIALID;//产品id
    private Date FDEMANDDATE;//需求日期
    @Transient
    private long FCUSTID;//
    @Transient
    private List<Map> products = new ArrayList<Map>();//具体需要的产品列表信息，最底层排序下来
    public long getFID() {
        return FID;
    }

    public void setFID(long FID) {
        this.FID = FID;
    }

    public String getFBILLNO() {
        return FBILLNO;
    }

    public void setFBILLNO(String FBILLNO) {
        this.FBILLNO = FBILLNO;
    }

    public String getFDOCUMENTSTATUS() {
        return FDOCUMENTSTATUS;
    }

    public void setFDOCUMENTSTATUS(String FDOCUMENTSTATUS) {
        this.FDOCUMENTSTATUS = FDOCUMENTSTATUS;
    }

    public BigDecimal getFFIRMQTY() {
        return FFIRMQTY;
    }

    public void setFFIRMQTY(BigDecimal FFIRMQTY) {
        this.FFIRMQTY = FFIRMQTY;
    }

    public String getFRELEASETYPE() {
        return FRELEASETYPE;
    }

    public void setFRELEASETYPE(String FRELEASETYPE) {
        this.FRELEASETYPE = FRELEASETYPE;
    }

    public long getFMATERIALID() {
        return FMATERIALID;
    }

    public void setFMATERIALID(long FMATERIALID) {
        this.FMATERIALID = FMATERIALID;
    }

    public Date getFDEMANDDATE() {
        return FDEMANDDATE;
    }

    public void setFDEMANDDATE(Date FDEMANDDATE) {
        this.FDEMANDDATE = FDEMANDDATE;
    }

    public List<Map> getProducts() {
        if(products!=null){
            Collections.sort(products, new Comparator<Map>() {
                @Override
                public int compare(Map obj1, Map obj2) {
                    int level1 = Integer.parseInt(obj1.get("level").toString());
                    int level2 = Integer.parseInt(obj2.get("level").toString());
                    return level1<level2?1:-1;
                }
            });
        }
        return products;
    }

    public void setProducts(List<Map> products) {
        this.products = products;
    }

    public long getFCUSTID() {
        return FCUSTID;
    }

    public void setFCUSTID(long FCUSTID) {
        this.FCUSTID = FCUSTID;
    }
}
