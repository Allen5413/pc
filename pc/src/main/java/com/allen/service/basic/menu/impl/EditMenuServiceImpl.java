package com.allen.service.basic.menu.impl;

import com.allen.base.exception.BusinessException;
import com.allen.dao.basic.menu.FindMenuDao;
import com.allen.dao.basic.menu.MenuDao;
import com.allen.entity.basic.Menu;
import com.allen.service.basic.menu.EditMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Service
public class EditMenuServiceImpl implements EditMenuService {

    @Resource
    private MenuDao menuDao;
    @Resource
    private FindMenuDao findMenuDao;

    @Override
    public void edit(Menu menu) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", menu.getName());
        Menu menuByName = findMenuDao.findByName(paramsMap);
        if(null != menuByName && menuByName.getId() != menu.getId()){
            throw new BusinessException("名称已存在！");
        }

        Menu oldMenu = menuDao.findOne(menu.getId());
        oldMenu.setName(menu.getName());
        oldMenu.setRemark(menu.getRemark());
        oldMenu.setOperator(menu.getOperator());
        oldMenu.setOperateTime(menu.getOperateTime());
        oldMenu.setVersion(menu.getVersion());
        menuDao.save(oldMenu);
    }
}
