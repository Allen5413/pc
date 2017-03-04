package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 产品类别 FLOCALEID 值为2052
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "t_bd_materialcategory_l")
public class ProductType {
    @Id
    @GeneratedValue
    private long FPKID;
    private long FCATEGORYID;
    private long FLOCALEID;
    private String FNAME;
    private String FDESCRIPTION;

    public long getFPKID() {
        return FPKID;
    }

    public void setFPKID(long FPKID) {
        this.FPKID = FPKID;
    }

    public long getFCATEGORYID() {
        return FCATEGORYID;
    }

    public void setFCATEGORYID(long FCATEGORYID) {
        this.FCATEGORYID = FCATEGORYID;
    }

    public long getFLOCALEID() {
        return FLOCALEID;
    }

    public void setFLOCALEID(long FLOCALEID) {
        this.FLOCALEID = FLOCALEID;
    }

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }

    public String getFDESCRIPTION() {
        return FDESCRIPTION;
    }

    public void setFDESCRIPTION(String FDESCRIPTION) {
        this.FDESCRIPTION = FDESCRIPTION;
    }
}
