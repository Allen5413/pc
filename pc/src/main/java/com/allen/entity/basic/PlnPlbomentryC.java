package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 包路径：com.allen.entity.basic
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-04-09 23:30
 */
@Entity
@Table(name="t_pln_plbomentry_c")
public class PlnPlbomentryC implements Serializable{
    @Id
    private long FID;
    private long FENTRYID;
    private String FISSUETYPE;
    private long FISSUEORGID;
    private String FISGETSCRAP;
    private String FBKFLTIME;
    private BigDecimal FOVERRATE;
    private String FISOVER;
    private String FISKITTING;
    private String FOWNERTYPEID;
    private long FOWNERID;
    private String FRESERVETYPE;
    private String FMTONO;
    private String FPROJECTNO;
    private long FOPERID;
    private long FPROCESSID;
    private String FPOSITIONNO;
    private long FOFFSETTIME;
    private String FOFFSETTIMETYPE;
    private String FISREPLACEABLE;
    private String FISKEYITEM;
    private long FSRCTRANSORGID;
    private long FSRCTRANSSTOCKID;
    private long FSRCTRANSLOCID;
    private long FLOT;
    private String FLOT_TEXT;
    private long FPRIORITY;
    private long FSUPPLYGROUP;
    private String FREPLACEPOLICY;
    private String FREPLACETYPE;
    private long  FREPLACEPRIORITY;
    private long FREPLACEGROUP;
    private long FSMID;
    private long FSMENTRYID;
    private String FOVERTYPE;
    private long FCHILDSUPPLYORGID;
    private String FOPTQUEUE;
    private String FISSKIP;
    private String FISMINISSUEQTY;
    private String FSUPPLYMODE;

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

    public String getFISSUETYPE() {
        return FISSUETYPE;
    }

    public void setFISSUETYPE(String FISSUETYPE) {
        this.FISSUETYPE = FISSUETYPE;
    }

    public long getFISSUEORGID() {
        return FISSUEORGID;
    }

    public void setFISSUEORGID(long FISSUEORGID) {
        this.FISSUEORGID = FISSUEORGID;
    }

    public String getFISGETSCRAP() {
        return FISGETSCRAP;
    }

    public void setFISGETSCRAP(String FISGETSCRAP) {
        this.FISGETSCRAP = FISGETSCRAP;
    }

    public String getFBKFLTIME() {
        return FBKFLTIME;
    }

    public void setFBKFLTIME(String FBKFLTIME) {
        this.FBKFLTIME = FBKFLTIME;
    }

    public BigDecimal getFOVERRATE() {
        return FOVERRATE;
    }

    public void setFOVERRATE(BigDecimal FOVERRATE) {
        this.FOVERRATE = FOVERRATE;
    }

    public String getFISOVER() {
        return FISOVER;
    }

    public void setFISOVER(String FISOVER) {
        this.FISOVER = FISOVER;
    }

    public String getFISKITTING() {
        return FISKITTING;
    }

    public void setFISKITTING(String FISKITTING) {
        this.FISKITTING = FISKITTING;
    }

    public String getFOWNERTYPEID() {
        return FOWNERTYPEID;
    }

    public void setFOWNERTYPEID(String FOWNERTYPEID) {
        this.FOWNERTYPEID = FOWNERTYPEID;
    }

    public long getFOWNERID() {
        return FOWNERID;
    }

    public void setFOWNERID(long FOWNERID) {
        this.FOWNERID = FOWNERID;
    }

    public String getFRESERVETYPE() {
        return FRESERVETYPE;
    }

    public void setFRESERVETYPE(String FRESERVETYPE) {
        this.FRESERVETYPE = FRESERVETYPE;
    }

    public String getFMTONO() {
        return FMTONO;
    }

    public void setFMTONO(String FMTONO) {
        this.FMTONO = FMTONO;
    }

    public String getFPROJECTNO() {
        return FPROJECTNO;
    }

    public void setFPROJECTNO(String FPROJECTNO) {
        this.FPROJECTNO = FPROJECTNO;
    }

    public long getFOPERID() {
        return FOPERID;
    }

    public void setFOPERID(long FOPERID) {
        this.FOPERID = FOPERID;
    }

    public long getFPROCESSID() {
        return FPROCESSID;
    }

    public void setFPROCESSID(long FPROCESSID) {
        this.FPROCESSID = FPROCESSID;
    }

    public String getFPOSITIONNO() {
        return FPOSITIONNO;
    }

    public void setFPOSITIONNO(String FPOSITIONNO) {
        this.FPOSITIONNO = FPOSITIONNO;
    }

    public long getFOFFSETTIME() {
        return FOFFSETTIME;
    }

    public void setFOFFSETTIME(long FOFFSETTIME) {
        this.FOFFSETTIME = FOFFSETTIME;
    }

    public String getFOFFSETTIMETYPE() {
        return FOFFSETTIMETYPE;
    }

    public void setFOFFSETTIMETYPE(String FOFFSETTIMETYPE) {
        this.FOFFSETTIMETYPE = FOFFSETTIMETYPE;
    }

    public String getFISREPLACEABLE() {
        return FISREPLACEABLE;
    }

    public void setFISREPLACEABLE(String FISREPLACEABLE) {
        this.FISREPLACEABLE = FISREPLACEABLE;
    }

    public String getFISKEYITEM() {
        return FISKEYITEM;
    }

    public void setFISKEYITEM(String FISKEYITEM) {
        this.FISKEYITEM = FISKEYITEM;
    }

    public long getFSRCTRANSORGID() {
        return FSRCTRANSORGID;
    }

    public void setFSRCTRANSORGID(long FSRCTRANSORGID) {
        this.FSRCTRANSORGID = FSRCTRANSORGID;
    }

    public long getFSRCTRANSSTOCKID() {
        return FSRCTRANSSTOCKID;
    }

    public void setFSRCTRANSSTOCKID(long FSRCTRANSSTOCKID) {
        this.FSRCTRANSSTOCKID = FSRCTRANSSTOCKID;
    }

    public long getFSRCTRANSLOCID() {
        return FSRCTRANSLOCID;
    }

    public void setFSRCTRANSLOCID(long FSRCTRANSLOCID) {
        this.FSRCTRANSLOCID = FSRCTRANSLOCID;
    }

    public long getFLOT() {
        return FLOT;
    }

    public void setFLOT(long FLOT) {
        this.FLOT = FLOT;
    }

    public String getFLOT_TEXT() {
        return FLOT_TEXT;
    }

    public void setFLOT_TEXT(String FLOT_TEXT) {
        this.FLOT_TEXT = FLOT_TEXT;
    }

    public long getFPRIORITY() {
        return FPRIORITY;
    }

    public void setFPRIORITY(long FPRIORITY) {
        this.FPRIORITY = FPRIORITY;
    }

    public long getFSUPPLYGROUP() {
        return FSUPPLYGROUP;
    }

    public void setFSUPPLYGROUP(long FSUPPLYGROUP) {
        this.FSUPPLYGROUP = FSUPPLYGROUP;
    }

    public String getFREPLACEPOLICY() {
        return FREPLACEPOLICY;
    }

    public void setFREPLACEPOLICY(String FREPLACEPOLICY) {
        this.FREPLACEPOLICY = FREPLACEPOLICY;
    }

    public String getFREPLACETYPE() {
        return FREPLACETYPE;
    }

    public void setFREPLACETYPE(String FREPLACETYPE) {
        this.FREPLACETYPE = FREPLACETYPE;
    }

    public long getFREPLACEPRIORITY() {
        return FREPLACEPRIORITY;
    }

    public void setFREPLACEPRIORITY(long FREPLACEPRIORITY) {
        this.FREPLACEPRIORITY = FREPLACEPRIORITY;
    }

    public long getFREPLACEGROUP() {
        return FREPLACEGROUP;
    }

    public void setFREPLACEGROUP(long FREPLACEGROUP) {
        this.FREPLACEGROUP = FREPLACEGROUP;
    }

    public long getFSMID() {
        return FSMID;
    }

    public void setFSMID(long FSMID) {
        this.FSMID = FSMID;
    }

    public long getFSMENTRYID() {
        return FSMENTRYID;
    }

    public void setFSMENTRYID(long FSMENTRYID) {
        this.FSMENTRYID = FSMENTRYID;
    }

    public String getFOVERTYPE() {
        return FOVERTYPE;
    }

    public void setFOVERTYPE(String FOVERTYPE) {
        this.FOVERTYPE = FOVERTYPE;
    }

    public long getFCHILDSUPPLYORGID() {
        return FCHILDSUPPLYORGID;
    }

    public void setFCHILDSUPPLYORGID(long FCHILDSUPPLYORGID) {
        this.FCHILDSUPPLYORGID = FCHILDSUPPLYORGID;
    }

    public String getFOPTQUEUE() {
        return FOPTQUEUE;
    }

    public void setFOPTQUEUE(String FOPTQUEUE) {
        this.FOPTQUEUE = FOPTQUEUE;
    }

    public String getFISSKIP() {
        return FISSKIP;
    }

    public void setFISSKIP(String FISSKIP) {
        this.FISSKIP = FISSKIP;
    }

    public String getFISMINISSUEQTY() {
        return FISMINISSUEQTY;
    }

    public void setFISMINISSUEQTY(String FISMINISSUEQTY) {
        this.FISMINISSUEQTY = FISMINISSUEQTY;
    }

    public String getFSUPPLYMODE() {
        return FSUPPLYMODE;
    }

    public void setFSUPPLYMODE(String FSUPPLYMODE) {
        this.FSUPPLYMODE = FSUPPLYMODE;
    }
}
