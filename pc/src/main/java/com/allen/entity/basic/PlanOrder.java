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
    @Transient
    private long FCUSTID;//
    @Transient
    private String FNUMBER;//产品编号
    @Transient
    private String FNAME;//产品名称
    @Transient
    private String FCATEGORYID;
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

}
