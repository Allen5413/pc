package com.allen.service.basic.plnplbomentryc.impl;
import com.allen.dao.basic.plnplbomentryc.PlnPlbomentryCDao;
import com.allen.dao.basic.zplnplbomentryc.ZPlnPlbomentryCDao;
import com.allen.entity.basic.PlnPlbomentryC;
import com.allen.entity.basic.ZPlnPlbomentryC;
import com.allen.service.basic.plnplbomentryc.AddPlnPlbomentryCService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 包路径：com.allen.service.basic.plnplbomentryc.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-04-09 23:44
 */
@Service
public class AddPlnPlbomentryCServiceImpl implements AddPlnPlbomentryCService {
    @Resource
    private PlnPlbomentryCDao plnPlbomentryCDao;
    @Resource
    private ZPlnPlbomentryCDao zPlnPlbomentryCDao;
    @Override
    public void add(long fId, Date demandDate, BigDecimal FBASEYIELDQTY,long fBomId) throws Exception {
        ZPlnPlbomentryC zPlnPlbomentryC = new ZPlnPlbomentryC();
        zPlnPlbomentryC.setColumn1(1);
        zPlnPlbomentryC = zPlnPlbomentryCDao.save(zPlnPlbomentryC);

        PlnPlbomentryC plnPlbomentryC = new PlnPlbomentryC();
        plnPlbomentryC.setFENTRYID(zPlnPlbomentryC.getId());
        plnPlbomentryC.setFID(fId);
        plnPlbomentryC.setFISSKIP("1");
        plnPlbomentryC.setFISSUEORGID(0);
        plnPlbomentryC.setFISGETSCRAP("1");
        plnPlbomentryC.setFOVERRATE(BigDecimal.ZERO);
        plnPlbomentryC.setFOVERRATE(BigDecimal.ZERO);
        plnPlbomentryC.setFISOVER("0");
        plnPlbomentryC.setFISKITTING("0");
        plnPlbomentryC.setFOWNERTYPEID("BD_OwnerOrg");
        plnPlbomentryC.setFOWNERTYPEID("0");
        plnPlbomentryC.setFRESERVETYPE("1");
        plnPlbomentryC.setFOPERID(10);
        plnPlbomentryC.setFPROCESSID(0);
        plnPlbomentryC.setFOFFSETTIME(0);
        plnPlbomentryC.setFOFFSETTIMETYPE("1");
        plnPlbomentryC.setFISREPLACEABLE("0");
        plnPlbomentryC.setFISKEYITEM("0");
        plnPlbomentryC.setFSRCTRANSORGID(0);
        plnPlbomentryC.setFSRCTRANSSTOCKID(0);
        plnPlbomentryC.setFSRCTRANSLOCID(0);
        plnPlbomentryC.setFLOT(0);
        plnPlbomentryC.setFPRIORITY(0);
        plnPlbomentryC.setFREPLACEPRIORITY(0);
        plnPlbomentryC.setFSMENTRYID(0);
        plnPlbomentryC.setFSMENTRYID(0);
        plnPlbomentryC.setFOVERTYPE("2");
        plnPlbomentryC.setFCHILDSUPPLYORGID(0);
        plnPlbomentryC.setFISSKIP("0");
        plnPlbomentryC.setFISMINISSUEQTY("0");
        plnPlbomentryC.setFBKFLTIME("0");
        plnPlbomentryC.setFISSUETYPE("1");
        plnPlbomentryC.setFSUPPLYMODE("0");
        plnPlbomentryCDao.save(plnPlbomentryC);

        zPlnPlbomentryCDao.delete(zPlnPlbomentryC.getId());
    }
}
