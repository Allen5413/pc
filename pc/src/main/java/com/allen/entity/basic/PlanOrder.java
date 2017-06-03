package com.allen.entity.basic;

import com.allen.util.DateUtil;

import javax.persistence.*;
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
    private String FRELEASETYPE;//数据类型  1为生产订单
    private long FMATERIALID;//产品id
    private Date FDEMANDDATE;//需求日期
    private String FDATASOURCE;//数据来源
    private BigDecimal FBASEDEMANDQTY;//基本单位需求量
    private BigDecimal FBASEORDERQTY;//基本单位计划量
    private BigDecimal FBASESUGQTY;//本单位建议量
    private BigDecimal FORDERQTY;//计划订单量
    private BigDecimal FDEMANDQTY;//需求数量
    private BigDecimal FSUGQTY;//建议订单量
    private BigDecimal FBASEFIRMQTY;//
    private String FFORMID;//事物类型
    private String FBILLTYPEID;//单据类型
    private long FDEMANDORGID;//需求组织
    private long FSUPPLYORGID;//供应组织
    private long FINSTOCKORGID;//入库组织
    private long FBOMID;//bom编号
    private long FSUPPLIERID;//供应商
    private long FPLANERID;//计划员
    private String FOWNERTYPEID;//货主类型
    private long FOWNERID;//货主id
    private String FCOMPUTERNO;//运行号
    private long FBASEUNITID;//基本单位
    private long FUNITID;//单位
    private Date FPLANSTARTDATE;//建议采购或开工时间
    private Date FPLANFINISHDATE;//建议到货或完成时间
    private Date FFIRMSTARTDATE;//确认采购或开工时间
    private Date FFIRMFINISHDATE;//确认到货或完成时间
    private long FCREATORID;
    private Date FCREATEDATE;
    private long FMODIFIERID;
    private Date FMODIFYDATE;
    private long FAPPROVERID;
    private long FAUXPROPID;
    private long FSUPPLYMATERIALID;
    private String FCOMPUTEID;
    private String FMTONO;
    private String FPROJECTNO;
    private String FPC;//反写排产信息
    @Transient
    private long FCUSTID;//
    @Transient
    private String FNUMBER;//产品编号
    @Transient
    private String FNAME;//产品名称
    @Transient
    private String FCATEGORYID;
    @Transient
    private int level;
    @Transient
    private List<String> childs = new ArrayList<String>();//记录子产品的

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

    public List<String> getChilds() {
        return childs;
    }

    public void setChilds(List<String> childs) {
        this.childs = childs;
    }

    public long getFCUSTID() {
        return FCUSTID;
    }

    public void setFCUSTID(long FCUSTID) {
        this.FCUSTID = FCUSTID;
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

    public String getFCATEGORYID() {
        return FCATEGORYID;
    }

    public void setFCATEGORYID(String FCATEGORYID) {
        this.FCATEGORYID = FCATEGORYID;
    }

    public String getFDATASOURCE() {
        return FDATASOURCE;
    }

    public void setFDATASOURCE(String FDATASOURCE) {
        this.FDATASOURCE = FDATASOURCE;
    }

    public BigDecimal getFBASEDEMANDQTY() {
        return FBASEDEMANDQTY;
    }

    public void setFBASEDEMANDQTY(BigDecimal FBASEDEMANDQTY) {
        this.FBASEDEMANDQTY = FBASEDEMANDQTY;
    }

    public BigDecimal getFBASEORDERQTY() {
        return FBASEORDERQTY;
    }

    public void setFBASEORDERQTY(BigDecimal FBASEORDERQTY) {
        this.FBASEORDERQTY = FBASEORDERQTY;
    }

    public BigDecimal getFBASESUGQTY() {
        return FBASESUGQTY;
    }

    public void setFBASESUGQTY(BigDecimal FBASESUGQTY) {
        this.FBASESUGQTY = FBASESUGQTY;
    }

    public BigDecimal getFORDERQTY() {
        return FORDERQTY;
    }

    public void setFORDERQTY(BigDecimal FORDERQTY) {
        this.FORDERQTY = FORDERQTY;
    }

    public BigDecimal getFDEMANDQTY() {
        return FDEMANDQTY;
    }

    public void setFDEMANDQTY(BigDecimal FDEMANDQTY) {
        this.FDEMANDQTY = FDEMANDQTY;
    }

    public BigDecimal getFSUGQTY() {
        return FSUGQTY;
    }

    public void setFSUGQTY(BigDecimal FSUGQTY) {
        this.FSUGQTY = FSUGQTY;
    }

    public BigDecimal getFBASEFIRMQTY() {
        return FBASEFIRMQTY;
    }

    public void setFBASEFIRMQTY(BigDecimal FBASEFIRMQTY) {
        this.FBASEFIRMQTY = FBASEFIRMQTY;
    }

    public String getFFORMID() {
        return FFORMID;
    }

    public void setFFORMID(String FFORMID) {
        this.FFORMID = FFORMID;
    }

    public String getFBILLTYPEID() {
        return FBILLTYPEID;
    }

    public void setFBILLTYPEID(String FBILLTYPEID) {
        this.FBILLTYPEID = FBILLTYPEID;
    }

    public long getFDEMANDORGID() {
        return FDEMANDORGID;
    }

    public void setFDEMANDORGID(long FDEMANDORGID) {
        this.FDEMANDORGID = FDEMANDORGID;
    }

    public long getFSUPPLYORGID() {
        return FSUPPLYORGID;
    }

    public void setFSUPPLYORGID(long FSUPPLYORGID) {
        this.FSUPPLYORGID = FSUPPLYORGID;
    }

    public long getFINSTOCKORGID() {
        return FINSTOCKORGID;
    }

    public void setFINSTOCKORGID(long FINSTOCKORGID) {
        this.FINSTOCKORGID = FINSTOCKORGID;
    }

    public long getFBOMID() {
        return FBOMID;
    }

    public void setFBOMID(long FBOMID) {
        this.FBOMID = FBOMID;
    }

    public long getFSUPPLIERID() {
        return FSUPPLIERID;
    }

    public void setFSUPPLIERID(long FSUPPLIERID) {
        this.FSUPPLIERID = FSUPPLIERID;
    }

    public long getFPLANERID() {
        return FPLANERID;
    }

    public void setFPLANERID(long FPLANERID) {
        this.FPLANERID = FPLANERID;
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

    public String getFCOMPUTERNO() {
        return FCOMPUTERNO;
    }

    public void setFCOMPUTERNO(String FCOMPUTERNO) {
        this.FCOMPUTERNO = FCOMPUTERNO;
    }

    public long getFBASEUNITID() {
        return FBASEUNITID;
    }

    public void setFBASEUNITID(long FBASEUNITID) {
        this.FBASEUNITID = FBASEUNITID;
    }

    public long getFUNITID() {
        return FUNITID;
    }

    public void setFUNITID(long FUNITID) {
        this.FUNITID = FUNITID;
    }

    public Date getFPLANSTARTDATE() {
        return FPLANSTARTDATE;
    }

    public void setFPLANSTARTDATE(Date FPLANSTARTDATE) {
        this.FPLANSTARTDATE = FPLANSTARTDATE;
    }

    public Date getFPLANFINISHDATE() {
        return FPLANFINISHDATE;
    }

    public void setFPLANFINISHDATE(Date FPLANFINISHDATE) {
        this.FPLANFINISHDATE = FPLANFINISHDATE;
    }

    public Date getFFIRMSTARTDATE() {
        return FFIRMSTARTDATE;
    }

    public void setFFIRMSTARTDATE(Date FFIRMSTARTDATE) {
        this.FFIRMSTARTDATE = FFIRMSTARTDATE;
    }

    public Date getFFIRMFINISHDATE() {
        return FFIRMFINISHDATE;
    }

    public void setFFIRMFINISHDATE(Date FFIRMFINISHDATE) {
        this.FFIRMFINISHDATE = FFIRMFINISHDATE;
    }

    public long getFCREATORID() {
        return FCREATORID;
    }

    public void setFCREATORID(long FCREATORID) {
        this.FCREATORID = FCREATORID;
    }

    public Date getFCREATEDATE() {
        return FCREATEDATE;
    }

    public void setFCREATEDATE(Date FCREATEDATE) {
        this.FCREATEDATE = FCREATEDATE;
    }

    public long getFMODIFIERID() {
        return FMODIFIERID;
    }

    public void setFMODIFIERID(long FMODIFIERID) {
        this.FMODIFIERID = FMODIFIERID;
    }

    public Date getFMODIFYDATE() {
        return FMODIFYDATE;
    }

    public void setFMODIFYDATE(Date FMODIFYDATE) {
        this.FMODIFYDATE = FMODIFYDATE;
    }

    public long getFAPPROVERID() {
        return FAPPROVERID;
    }

    public void setFAPPROVERID(long FAPPROVERID) {
        this.FAPPROVERID = FAPPROVERID;
    }

    public long getFAUXPROPID() {
        return FAUXPROPID;
    }

    public void setFAUXPROPID(long FAUXPROPID) {
        this.FAUXPROPID = FAUXPROPID;
    }

    public long getFSUPPLYMATERIALID() {
        return FSUPPLYMATERIALID;
    }

    public void setFSUPPLYMATERIALID(long FSUPPLYMATERIALID) {
        this.FSUPPLYMATERIALID = FSUPPLYMATERIALID;
    }

    public String getFCOMPUTEID() {
        return FCOMPUTEID;
    }

    public void setFCOMPUTEID(String FCOMPUTEID) {
        this.FCOMPUTEID = FCOMPUTEID;
    }

    //获取当前产品的下级产品
    public List<String[]> getSelfChilds(){
        List<String[]> selfChids = null;
        if(childs!=null&&childs.size()>0){
            selfChids = new ArrayList<String[]>();
            for(String str:childs){
                String[] materialAttr = str.split(",");
                //产品是自制半成品 产成品
                if("239".equals(materialAttr[2])||"241".equals(materialAttr[2])){
                    selfChids.add(materialAttr);
                }
            }
        }
        return  selfChids;
    }

    public String getDemandDate(){
        return DateUtil.getFormattedString(this.FDEMANDDATE,DateUtil.shortDatePattern);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getFPC() {
        return FPC;
    }

    public void setFPC(String FPC) {
        this.FPC = FPC;
    }
}
