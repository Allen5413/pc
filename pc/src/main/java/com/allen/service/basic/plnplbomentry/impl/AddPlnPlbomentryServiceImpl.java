package com.allen.service.basic.plnplbomentry.impl;

import com.allen.dao.basic.planorder.FindPlanOrderDao;
import com.allen.dao.basic.plnplbomentry.PlnPlbomentryDao;
import com.allen.dao.basic.zplnplbomentry.ZPlnPlbomentryDao;
import com.allen.entity.basic.PlnPlbomentry;
import com.allen.entity.basic.ZPlnPlbomentry;
import com.allen.service.basic.plnplbomentry.AddPlnPlbomentryService;
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
public class AddPlnPlbomentryServiceImpl implements AddPlnPlbomentryService {
    @Resource
    private PlnPlbomentryDao plnPlbomentryDao;
    @Resource
    private FindPlanOrderDao findPlanOrderDao;
    @Resource
    private ZPlnPlbomentryDao zPlnPlbomentryDao;
    @Override
    public PlnPlbomentry add(long fId, Date demandDate,BigDecimal FBASEYIELDQTY,long fBomId,long fMaterialId) throws Exception {
        ZPlnPlbomentry zPlnPlbomentry = new ZPlnPlbomentry();
        zPlnPlbomentry.setColumn1(1);
        zPlnPlbomentry = zPlnPlbomentryDao.save(zPlnPlbomentry);

        PlnPlbomentry plnPlbomentry = new PlnPlbomentry();
        plnPlbomentry.setFENTRYID(zPlnPlbomentry.getId());
        plnPlbomentry.setFID(fId);
        plnPlbomentry.setFSEQ(1);
        plnPlbomentry.setFMATERIALID(fMaterialId);
        plnPlbomentry.setFMATERIALTYPE("1");
        plnPlbomentry.setFDOSAGETYPE("2");
        plnPlbomentry.setFAUXPROPID(0);
        plnPlbomentry.setFUSERATE(new BigDecimal(100));
        plnPlbomentry.setFDEMANDDATE(demandDate);
        plnPlbomentry.setFSTDQTY(FBASEYIELDQTY);
        plnPlbomentry.setFNEEDQTY(FBASEYIELDQTY);
        plnPlbomentry.setFMUSTQTY(FBASEYIELDQTY);
        plnPlbomentry.setFFIXSCRAP(new BigDecimal(0));
        plnPlbomentry.setFSCRAPRATE(BigDecimal.ZERO);
        plnPlbomentry.setFBOMID(fBomId);
        plnPlbomentry.setFSTOCKID(0);
        plnPlbomentry.setFSTOCKLOCID(0);
        plnPlbomentry.setFEXTENDCONTROL(0);
        plnPlbomentry.setFDENOMINATOR(new BigDecimal(1));
        plnPlbomentry.setFNUMERATOR(new BigDecimal(1));
        plnPlbomentry.setFBASESTDQTY(FBASEYIELDQTY);
        plnPlbomentry.setFBASENEEDQTY(FBASEYIELDQTY);
        plnPlbomentry.setFBASEMUSTQTY(FBASEYIELDQTY);
        plnPlbomentry.setFBASEFIXSCRAPQTY(FBASEYIELDQTY);
        plnPlbomentry.setFBASENUMERATOR(new BigDecimal(1));
        plnPlbomentry.setFBASEDENOMINATOR(new BigDecimal(1));
        plnPlbomentry.setFISSUBSITEM("0");
        plnPlbomentry.setFUNITID(findPlanOrderDao.findBaseUnitId(fMaterialId));
        plnPlbomentry.setFBOMENTRYID(findPlanOrderDao.findBomChildEntryId(fMaterialId));
        plnPlbomentry.setFBASEUNITID(findPlanOrderDao.findBaseUnitId(fMaterialId));
        plnPlbomentryDao.save(plnPlbomentry);

        zPlnPlbomentryDao.delete(zPlnPlbomentry.getId());
        return plnPlbomentry;
    }
}
