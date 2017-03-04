package com.allen.entity.basic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品信息基本信息
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "t_bd_materialbase")
public class Product {
    @Id
    @GeneratedValue
    private long FENTRYID;
    private long FMATERIALID;//产品id
    private String FERPCLSID;//产品属性
    private long FCATEGORYID;//产品类别id
    @Transient
    private String FNUMBER;//产品编码
    @Transient
    private String FNAME;//产品名称
    @Transient
    private String cateGoryName;//产品类别名称
    @Transient
    private List<ProductSelfUse> productSelfUses = new ArrayList<ProductSelfUse>();

    public long getFENTRYID() {
        return FENTRYID;
    }

    public void setFENTRYID(long FENTRYID) {
        this.FENTRYID = FENTRYID;
    }

    public long getFMATERIALID() {
        return FMATERIALID;
    }

    public void setFMATERIALID(long FMATERIALID) {
        this.FMATERIALID = FMATERIALID;
    }

    public String getFERPCLSID() {
        return FERPCLSID;
    }

    public void setFERPCLSID(String FERPCLSID) {
        this.FERPCLSID = FERPCLSID;
    }

    public long getFCATEGORYID() {
        return FCATEGORYID;
    }

    public void setFCATEGORYID(long FCATEGORYID) {
        this.FCATEGORYID = FCATEGORYID;
    }

    public String getFNUMBER() {
        return FNUMBER;
    }

    public void setFNUMBER(String FNUMBER) {
        this.FNUMBER = FNUMBER;
    }

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }

    public String getCateGoryName() {
        return cateGoryName;
    }

    public void setCateGoryName(String cateGoryName) {
        this.cateGoryName = cateGoryName;
    }

    public List<ProductSelfUse> getProductSelfUses() {
        return productSelfUses;
    }

    public void setProductSelfUses(List<ProductSelfUse> productSelfUses) {
        this.productSelfUses = productSelfUses;
    }
}
