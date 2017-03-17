package com.allen.service.basic.materialstock;

import com.allen.entity.basic.MaterialStock;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Allen on 2017/3/16 0016.
 */
public interface FindStockByFmaterialIdsService {

    /**
     * 通过多个产品id，查询各自的库存信息
     * @param fmaterialIds
     * @return 返回格式 Map<产品id, MaterialStock>
     * @throws Exception
     */
    public Map<Long, MaterialStock> find(Long... fmaterialIds)throws Exception;
}
