package com.allen.service.basic.producelinecoreproductcg.impl;

import com.allen.dao.basic.producelinecoreproductcg.ProduceLineCoreProductCgDao;
import com.allen.entity.basic.ProduceLineCoreProductCg;
import com.allen.service.basic.producelinecoreproductcg.AddPlcpcgService;
import com.allen.service.basic.producelinecoreproductcg.DelPlcpcgByIdService;
import com.allen.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddPlcpcgServiceImpl implements AddPlcpcgService{

    @Resource
    private DelPlcpcgByIdService delPlcpcgByIdService;
    @Resource
    private ProduceLineCoreProductCgDao produceLineCoreProductCgDao;

    @Override
    @Transactional
    public void add(long plcpId, String delPlcpcgIds, Long[] plcpcgIds, Integer[] snos, Long[] cgIds,
                    Long[] wmIds, Float[] unitTimeCapacitys, Integer[] minBatchs, String loginName) throws Exception {
        //删掉的关联班组
        if(!StringUtil.isEmpty(delPlcpcgIds)){
            for(String delPlcpcgId : delPlcpcgIds.split(",")){
                delPlcpcgByIdService.del(Long.parseLong(delPlcpcgId));
            }
        }
        if(null != plcpcgIds && 0 < plcpcgIds.length) {

            for(int i=0; i < plcpcgIds.length; i++) {
                long plcpcgId = plcpcgIds[i];
                int sno = snos[i];
                long cgId = cgIds[i];
                long wmId = wmIds[i];
                int unitTimeCapacity = (int)(unitTimeCapacitys[i]*3600);
                int minBatch = minBatchs[i];

                //==0说明是新增的班组信息，否则就是之前关联的班组
                if(0 == plcpcgId){
                    ProduceLineCoreProductCg produceLineCoreProductCg = new ProduceLineCoreProductCg();
                    produceLineCoreProductCg.setProduceLineCoreProductId(plcpId);
                    produceLineCoreProductCg.setWorkModeId(wmId);
                    produceLineCoreProductCg.setClassGroupId(cgId);
                    produceLineCoreProductCg.setMinBatch(minBatch);
                    produceLineCoreProductCg.setSno(sno);
                    produceLineCoreProductCg.setUnitTimeCapacity(unitTimeCapacity);
                    produceLineCoreProductCg.setOperator(loginName);
                    produceLineCoreProductCgDao.save(produceLineCoreProductCg);
                }else {
                    ProduceLineCoreProductCg produceLineCoreProductCg = produceLineCoreProductCgDao.findOne(plcpcgId);
                    //如果班组没变，只修改其他信息；否则删除班组信息重新关联
                    if(produceLineCoreProductCg.getClassGroupId() != cgId){
                        delPlcpcgByIdService.del(produceLineCoreProductCg.getId());
                        produceLineCoreProductCg = new ProduceLineCoreProductCg();
                        produceLineCoreProductCg.setProduceLineCoreProductId(plcpId);
                        produceLineCoreProductCg.setClassGroupId(cgId);
                    }
                    produceLineCoreProductCg.setWorkModeId(wmId);
                    produceLineCoreProductCg.setSno(sno);
                    produceLineCoreProductCg.setMinBatch(minBatch);
                    produceLineCoreProductCg.setUnitTimeCapacity(unitTimeCapacity);
                    produceLineCoreProductCg.setOperator(loginName);
                    produceLineCoreProductCgDao.save(produceLineCoreProductCg);
                }
            }
        }
    }
}
