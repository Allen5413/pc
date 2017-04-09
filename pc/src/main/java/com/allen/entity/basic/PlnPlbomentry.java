package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 包路径：com.allen.entity.basic
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-04-09 22:27
 */
@Entity
@Table(name="t_pln_plbomentry")
public class PlnPlbomentry implements Serializable{
    @Id
    private long FID;
    private long FENTRYID;
    private long FSEQ;
    private long FMATERIALID;
    private String FMATERIALTYPE;
    private String FDOSAGETYPE;
    private int FAUXPROPID;
    private BigDecimal FUSERATE;
    private BigDecimal FNUMERATOR;
    private BigDecimal FDENOMINATOR;
    private Date FDEMANDDATE;
    private long FUNITID;
    private BigDecimal FSTDQTY;
    private BigDecimal FNEEDQTY;
    private BigDecimal FMUSTQTY;
    private BigDecimal FFIXSCRAP;
    private BigDecimal FSCRAPRATE;
    private long FBOMID;
    private long FSTOCKID;
    private long FSTOCKLOCID;
    private long FWORKCALID;
    private long FBOMENTRYID;
    private long FBASEUNITID;
    private BigDecimal FBASESTDQTY;
    private BigDecimal FBASENEEDQTY;
    private BigDecimal FBASEMUSTQTY;
    private BigDecimal FBASEFIXSCRAPQTY;
    private BigDecimal FBASENUMERATOR;
    private BigDecimal FBASEDENOMINATOR;
    private long FSUBSGROUP;
    private String FISSUBSITEM;
    private long FMAINGROUP;
    private long FSUBSENTRYID;
    private long FSUBSINTERID;
    private String FENTRYPKID;
    private long FEXTENDCONTROL;

    public long getFID() {
        return FID;
    }

    public void setFID(long FID) {
        this.FID = FID;
    }

    public long getFENTRYID() {
        return FENTRYID;
    }

    public void setFENTRYID(long FENTRYID) {
        this.FENTRYID = FENTRYID;
    }

    public long getFSEQ() {
        return FSEQ;
    }

    public void setFSEQ(long FSEQ) {
        this.FSEQ = FSEQ;
    }

    public long getFMATERIALID() {
        return FMATERIALID;
    }

    public void setFMATERIALID(long FMATERIALID) {
        this.FMATERIALID = FMATERIALID;
    }

    public String getFMATERIALTYPE() {
        return FMATERIALTYPE;
    }

    public void setFMATERIALTYPE(String FMATERIALTYPE) {
        this.FMATERIALTYPE = FMATERIALTYPE;
    }

    public String getFDOSAGETYPE() {
        return FDOSAGETYPE;
    }

    public void setFDOSAGETYPE(String FDOSAGETYPE) {
        this.FDOSAGETYPE = FDOSAGETYPE;
    }

    public int getFAUXPROPID() {
        return FAUXPROPID;
    }

    public void setFAUXPROPID(int FAUXPROPID) {
        this.FAUXPROPID = FAUXPROPID;
    }

    public BigDecimal getFUSERATE() {
        return FUSERATE;
    }

    public void setFUSERATE(BigDecimal FUSERATE) {
        this.FUSERATE = FUSERATE;
    }

    public BigDecimal getFNUMERATOR() {
        return FNUMERATOR;
    }

    public void setFNUMERATOR(BigDecimal FNUMERATOR) {
        this.FNUMERATOR = FNUMERATOR;
    }

    public BigDecimal getFDENOMINATOR() {
        return FDENOMINATOR;
    }

    public void setFDENOMINATOR(BigDecimal FDENOMINATOR) {
        this.FDENOMINATOR = FDENOMINATOR;
    }

    public Date getFDEMANDDATE() {
        return FDEMANDDATE;
    }

    public void setFDEMANDDATE(Date FDEMANDDATE) {
        this.FDEMANDDATE = FDEMANDDATE;
    }

    public long getFUNITID() {
        return FUNITID;
    }

    public void setFUNITID(long FUNITID) {
        this.FUNITID = FUNITID;
    }

    public BigDecimal getFSTDQTY() {
        return FSTDQTY;
    }

    public void setFSTDQTY(BigDecimal FSTDQTY) {
        this.FSTDQTY = FSTDQTY;
    }

    public BigDecimal getFNEEDQTY() {
        return FNEEDQTY;
    }

    public void setFNEEDQTY(BigDecimal FNEEDQTY) {
        this.FNEEDQTY = FNEEDQTY;
    }

    public BigDecimal getFMUSTQTY() {
        return FMUSTQTY;
    }

    public void setFMUSTQTY(BigDecimal FMUSTQTY) {
        this.FMUSTQTY = FMUSTQTY;
    }

    public BigDecimal getFFIXSCRAP() {
        return FFIXSCRAP;
    }

    public void setFFIXSCRAP(BigDecimal FFIXSCRAP) {
        this.FFIXSCRAP = FFIXSCRAP;
    }

    public BigDecimal getFSCRAPRATE() {
        return FSCRAPRATE;
    }

    public void setFSCRAPRATE(BigDecimal FSCRAPRATE) {
        this.FSCRAPRATE = FSCRAPRATE;
    }

    public long getFBOMID() {
        return FBOMID;
    }

    public void setFBOMID(long FBOMID) {
        this.FBOMID = FBOMID;
    }

    public long getFSTOCKID() {
        return FSTOCKID;
    }

    public void setFSTOCKID(long FSTOCKID) {
        this.FSTOCKID = FSTOCKID;
    }

    public long getFSTOCKLOCID() {
        return FSTOCKLOCID;
    }

    public void setFSTOCKLOCID(long FSTOCKLOCID) {
        this.FSTOCKLOCID = FSTOCKLOCID;
    }

    public long getFWORKCALID() {
        return FWORKCALID;
    }

    public void setFWORKCALID(long FWORKCALID) {
        this.FWORKCALID = FWORKCALID;
    }

    public long getFBOMENTRYID() {
        return FBOMENTRYID;
    }

    public void setFBOMENTRYID(long FBOMENTRYID) {
        this.FBOMENTRYID = FBOMENTRYID;
    }

    public long getFBASEUNITID() {
        return FBASEUNITID;
    }

    public void setFBASEUNITID(long FBASEUNITID) {
        this.FBASEUNITID = FBASEUNITID;
    }

    public BigDecimal getFBASESTDQTY() {
        return FBASESTDQTY;
    }

    public void setFBASESTDQTY(BigDecimal FBASESTDQTY) {
        this.FBASESTDQTY = FBASESTDQTY;
    }

    public BigDecimal getFBASENEEDQTY() {
        return FBASENEEDQTY;
    }

    public void setFBASENEEDQTY(BigDecimal FBASENEEDQTY) {
        this.FBASENEEDQTY = FBASENEEDQTY;
    }

    public BigDecimal getFBASEMUSTQTY() {
        return FBASEMUSTQTY;
    }

    public void setFBASEMUSTQTY(BigDecimal FBASEMUSTQTY) {
        this.FBASEMUSTQTY = FBASEMUSTQTY;
    }

    public BigDecimal getFBASEFIXSCRAPQTY() {
        return FBASEFIXSCRAPQTY;
    }

    public void setFBASEFIXSCRAPQTY(BigDecimal FBASEFIXSCRAPQTY) {
        this.FBASEFIXSCRAPQTY = FBASEFIXSCRAPQTY;
    }

    public BigDecimal getFBASENUMERATOR() {
        return FBASENUMERATOR;
    }

    public void setFBASENUMERATOR(BigDecimal FBASENUMERATOR) {
        this.FBASENUMERATOR = FBASENUMERATOR;
    }

    public BigDecimal getFBASEDENOMINATOR() {
        return FBASEDENOMINATOR;
    }

    public void setFBASEDENOMINATOR(BigDecimal FBASEDENOMINATOR) {
        this.FBASEDENOMINATOR = FBASEDENOMINATOR;
    }

    public long getFSUBSGROUP() {
        return FSUBSGROUP;
    }

    public void setFSUBSGROUP(long FSUBSGROUP) {
        this.FSUBSGROUP = FSUBSGROUP;
    }

    public String getFISSUBSITEM() {
        return FISSUBSITEM;
    }

    public void setFISSUBSITEM(String FISSUBSITEM) {
        this.FISSUBSITEM = FISSUBSITEM;
    }

    public long getFMAINGROUP() {
        return FMAINGROUP;
    }

    public void setFMAINGROUP(long FMAINGROUP) {
        this.FMAINGROUP = FMAINGROUP;
    }

    public long getFSUBSENTRYID() {
        return FSUBSENTRYID;
    }

    public void setFSUBSENTRYID(long FSUBSENTRYID) {
        this.FSUBSENTRYID = FSUBSENTRYID;
    }

    public long getFSUBSINTERID() {
        return FSUBSINTERID;
    }

    public void setFSUBSINTERID(long FSUBSINTERID) {
        this.FSUBSINTERID = FSUBSINTERID;
    }

    public String getFENTRYPKID() {
        return FENTRYPKID;
    }

    public void setFENTRYPKID(String FENTRYPKID) {
        this.FENTRYPKID = FENTRYPKID;
    }

    public long getFEXTENDCONTROL() {
        return FEXTENDCONTROL;
    }

    public void setFEXTENDCONTROL(long FEXTENDCONTROL) {
        this.FEXTENDCONTROL = FEXTENDCONTROL;
    }
}
