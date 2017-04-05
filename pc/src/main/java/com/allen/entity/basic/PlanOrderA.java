package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 包路径：com.allen.entity.basic
 * 功能说明：计划订单表
 * 创建人： ly
 * 创建时间: 2017-03-04 22:34
 */
@Entity
@Table(name="t_pln_planorder_a")
public class PlanOrderA implements Serializable {
    @Id
    private long FENTRYID;      //主键
    private long FID;           //外键
    private char FISSKIP;       //是否虚拟件或跳层
    private String FMRPNOTE;    //计划标签
    private BigDecimal FYIELDRATE;//成品率
    private BigDecimal FBASEYIELDQTY;//基本单位成品数量
    private int FEXTENDCONTROL; //净需求扩展属性
    private String FRELEASEBILLTYPE;//投放单据类型

    public long getFENTRYID() {
        return FENTRYID;
    }

    public void setFENTRYID(long FENTRYID) {
        this.FENTRYID = FENTRYID;
    }

    public long getFID() {
        return FID;
    }

    public void setFID(long FID) {
        this.FID = FID;
    }

    public char getFISSKIP() {
        return FISSKIP;
    }

    public void setFISSKIP(char FISSKIP) {
        this.FISSKIP = FISSKIP;
    }

    public String getFMRPNOTE() {
        return FMRPNOTE;
    }

    public void setFMRPNOTE(String FMRPNOTE) {
        this.FMRPNOTE = FMRPNOTE;
    }

    public BigDecimal getFYIELDRATE() {
        return FYIELDRATE;
    }

    public void setFYIELDRATE(BigDecimal FYIELDRATE) {
        this.FYIELDRATE = FYIELDRATE;
    }

    public BigDecimal getFBASEYIELDQTY() {
        return FBASEYIELDQTY;
    }

    public void setFBASEYIELDQTY(BigDecimal FBASEYIELDQTY) {
        this.FBASEYIELDQTY = FBASEYIELDQTY;
    }

    public int getFEXTENDCONTROL() {
        return FEXTENDCONTROL;
    }

    public void setFEXTENDCONTROL(int FEXTENDCONTROL) {
        this.FEXTENDCONTROL = FEXTENDCONTROL;
    }

    public String getFRELEASEBILLTYPE() {
        return FRELEASEBILLTYPE;
    }

    public void setFRELEASEBILLTYPE(String FRELEASEBILLTYPE) {
        this.FRELEASEBILLTYPE = FRELEASEBILLTYPE;
    }
}
