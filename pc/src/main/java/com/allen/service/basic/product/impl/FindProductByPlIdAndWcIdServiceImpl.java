package com.allen.service.basic.product.impl;

import com.allen.dao.basic.product.FindProductDao;
import com.allen.entity.pojo.product.ProductBean;
import com.allen.service.basic.product.FindProductByPlIdAndWcIdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Service
public class FindProductByPlIdAndWcIdServiceImpl implements FindProductByPlIdAndWcIdService {

    @Resource
    private FindProductDao findProductDao;

    @Override
    public List<ProductBean> find(Map<String, Object> queryParams) throws Exception {
        return findProductDao.findByPlIdAndWcId(queryParams);
    }
}
