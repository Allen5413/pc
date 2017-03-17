package com.allen.service.basic.materialstock.impl;

import com.allen.dao.basic.materialstock.FindMaterialStockDao;
import com.allen.entity.basic.MaterialStock;
import com.allen.service.basic.materialstock.FindStockByFmaterialIdsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/16 0016.
 */
@Service
public class FindStockByFmaterialIdsServiceImpl implements FindStockByFmaterialIdsService {

    @Resource
    private FindMaterialStockDao findMaterialStockDao;

    @Override
    public Map<Long, MaterialStock> find(Long... fmaterialIds) throws Exception {
        Map<Long, MaterialStock> returnMap = null;
        List<Map> resultList = findMaterialStockDao.findByFmaterialIds(fmaterialIds);
        if(null != resultList && 0 < resultList.size()){
            returnMap = new HashMap<Long, MaterialStock>(resultList.size());
            for(Map map : resultList){
                MaterialStock materialStock = new MaterialStock();
                long fmaterialId = (Long) map.get("FMATERIALID");
                BigDecimal safe = (BigDecimal) map.get("FSAFESTOCK");
                BigDecimal min = (BigDecimal) map.get("FMINSTOCK");
                BigDecimal max = (BigDecimal) map.get("FMAXSTOCK");
                materialStock.setFMATERIALID(fmaterialId);
                materialStock.setFSAFESTOCK(safe);
                materialStock.setFMINSTOCK(min);
                materialStock.setFMAXSTOCK(max);
                returnMap.put(fmaterialId, materialStock);
            }
        }
        return returnMap;
    }
}
