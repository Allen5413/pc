package com.allen.dao.basic.materialstock;

import com.allen.dao.BaseQueryDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/16 0016.
 */
@Service
public class FindMaterialStockDao extends BaseQueryDao {

    /**
     * 传入多个产品id，查询他们各自的库存信息
     * @param fmaterialIds
     * @return
     * @throws Exception
     */
    public List<Map> findByFmaterialIds(Long... fmaterialIds)throws Exception{
        String sql = "select s.FMATERIALID, s.FSAFESTOCK, s.FMINSTOCK, s.FMAXSTOCK ";
        sql += "from t_bd_materialstock s where 1=1 ";
        if(null != fmaterialIds && 0 < fmaterialIds.length){
            sql += "and s.FMATERIALID in (";
            for(int i=0; i<fmaterialIds.length; i++){
                sql += "?";
                if(i < fmaterialIds.length - 1){
                    sql += ",";
                }
            }
            sql +=")";
        }
        List<Map> list = super.sqlQueryByNativeSqlToMap(sql.toString(), fmaterialIds);
        return list;
    }
}
