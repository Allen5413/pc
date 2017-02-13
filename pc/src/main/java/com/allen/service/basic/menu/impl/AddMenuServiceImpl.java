package com.allen.service.basic.menu.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.menu.FindMenuDao;
import com.allen.dao.basic.menu.MenuDao;
import com.allen.entity.basic.Menu;
import com.allen.service.basic.menu.AddMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class AddMenuServiceImpl implements AddMenuService {

    @Resource
    private MenuDao menuDao;
    @Resource
    private FindMenuDao findMenuDao;

    @Override
    public void add(Menu menu) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", menu.getName());
        Menu menuByName = findMenuDao.findByName(paramsMap);
        if(null != menuByName){
            throw new BusinessException("名称已存在！");
        }
        menuDao.save(menu);
    }
}
