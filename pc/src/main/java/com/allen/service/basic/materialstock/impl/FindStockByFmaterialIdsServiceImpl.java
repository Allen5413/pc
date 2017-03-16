package com.allen.service.basic.materialstock.impl;

import com.allen.dao.basic.materialstock.FindMaterialStockDao;
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
    public Map<Long, Map<String, BigDecimal>> find(Long... fmaterialIds) throws Exception {
        Map<Long, Map<String, BigDecimal>> returnMap = null;
        List<Map> resultList = findMaterialStockDao.findByFmaterialIds(fmaterialIds);
        if(null != resultList && 0 < resultList.size()){
            returnMap = new HashMap<Long, Map<String, BigDecimal>>(resultList.size());
            Map<String, BigDecimal> stockMap = null;
            for(Map map : resultList){
                stockMap = new HashMap<String, BigDecimal>(3);
                long fmaterialId = (Long) map.get("FMATERIALID");
                BigDecimal safe = (BigDecimal) map.get("FSAFESTOCK");
                BigDecimal min = (BigDecimal) map.get("FMINSTOCK");
                BigDecimal max = (BigDecimal) map.get("FMAXSTOCK");
                stockMap.put("safe", null == safe ? new BigDecimal(0) : safe);
                stockMap.put("min", null == min ? new BigDecimal(0) : min);
                stockMap.put("max", null == max ? new BigDecimal(0) : max);
                returnMap.put(fmaterialId, stockMap);
            }
        }
        return returnMap;
    }
}
