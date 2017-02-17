package com.allen.service.basic.menu.impl;

import com.allen.dao.PageInfo;
import com.allen.dao.basic.menu.FindMenuDao;
import com.allen.dao.basic.menu.MenuDao;
import com.allen.dao.basic.resource.ResourceDao;
import com.allen.entity.basic.Menu;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.menu.FindMenuPageService;
import com.allen.service.basic.menu.FindMenuTreeService;
import com.allen.util.EntityTree;
import com.allen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Service
public class FindMenuTreeServiceImpl implements FindMenuTreeService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private ResourceDao resourceDao;
    @Override
    public List<EntityTree> findMenuToTree() {
        List<EntityTree> entityTrees = new ArrayList<EntityTree>();
        //查找所有菜单
        List<Menu> menus = (List<Menu>) menuDao.findAll();
        if(menus==null){
            return  entityTrees;
        }
        //查找所有菜单资源
        List<Resource> resources = (List<Resource>)resourceDao.findAll();
        EntityTree entityTree = null;
        int menuId = -1;
        Map<String,String> menuMap = new HashMap<String, String>();
        for (Menu menu:menus){
            entityTree = new EntityTree();
            entityTree.setName(menu.getName());
            entityTree.setpId("0");
            entityTree.setId(menuId--+"");
            menuMap.put(menu.getId()+"",entityTree.getId());
            entityTrees.add(entityTree);
        }
        String pId = "";
        for (Resource resource:resources){
            pId = resource.getParentId()+"";
            if (resource.getParentId()==0&&!StringUtil.isEmpty(menuMap.get(resource.getMenuId()+""))){
                pId = menuMap.get(resource.getMenuId()+"");
            }
            entityTree = new EntityTree();
            entityTree.setName(resource.getName());
            entityTree.setpId(pId);
            entityTree.setId(resource.getId()+"");
            entityTrees.add(entityTree);
        }
        return entityTrees;
    }
}
