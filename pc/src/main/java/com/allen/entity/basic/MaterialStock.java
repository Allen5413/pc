package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 产品库存
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "t_bd_materialstock")
public class MaterialStock {
    @Id
    private long FENTRYID;
    private long FMATERIALID;
    private BigDecimal FSAFESTOCK;
    private BigDecimal FMINSTOCK;
    private BigDecimal FMAXSTOCK;
    @Transient
    private BigDecimal FSORENUM = new BigDecimal(100);

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

    public BigDecimal getFSAFESTOCK() {
        return FSAFESTOCK;
    }

    public void setFSAFESTOCK(BigDecimal FSAFESTOCK) {
        this.FSAFESTOCK = FSAFESTOCK;
    }

    public BigDecimal getFMINSTOCK() {
        return FMINSTOCK;
    }

    public void setFMINSTOCK(BigDecimal FMINSTOCK) {
        this.FMINSTOCK = FMINSTOCK;
    }

    public BigDecimal getFMAXSTOCK() {
        return FMAXSTOCK;
    }

    public void setFMAXSTOCK(BigDecimal FMAXSTOCK) {
        this.FMAXSTOCK = FMAXSTOCK;
    }

    public BigDecimal getFSORENUM() {
        return FSORENUM;
    }

    public void setFSORENUM(BigDecimal FSORENUM) {
        this.FSORENUM = FSORENUM;
    }
}
