package com.allen.entity.basic;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品自制件用量信息
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "t_eng_bom")
public class ProductSelfUse {
    @Id
    private long FID;
    private long FMATERIALID;//产品id
    @Transient
    private String FNUMBER;//产品编码
    @Transient
    private String FNAME;//产品名称
    @Transient
    private BigDecimal useQty ;//产品实际用量
    @Transient
    private BigDecimal FOFFSETTIME;//产品偏执时间
    @Transient
    private long FSEQ;//工序

    public long getFID() {
        return FID;
    }

    public void setFID(long FID) {
        this.FID = FID;
    }

    public long getFMATERIALID() {
        return FMATERIALID;
    }

    public void setFMATERIALID(long FMATERIALID) {
        this.FMATERIALID = FMATERIALID;
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

    public BigDecimal getUseQty() {
        return useQty;
    }

    public void setUseQty(BigDecimal useQty) {
        this.useQty = useQty;
    }

    public BigDecimal getFOFFSETTIME() {
        return FOFFSETTIME;
    }

    public void setFOFFSETTIME(BigDecimal FOFFSETTIME) {
        this.FOFFSETTIME = FOFFSETTIME;
    }

    public long getFSEQ() {
        return FSEQ;
    }

    public void setFSEQ(long FSEQ) {
        this.FSEQ = FSEQ;
    }
}
