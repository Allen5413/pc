package com.allen.web.controller.user.usergroupresource;

import com.alibaba.fastjson.JSON;
import com.allen.service.basic.menu.FindMenuTreeService;
import com.allen.service.user.usergroup.FindAllUserGroupService;
import com.allen.util.EntityTree;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lenovo on 2017/2/15.
 */
@Controller
@RequestMapping(value = "/showUserGroupResource")
public class ShowUserGroupResourceController {
    @Resource
    private FindAllUserGroupService findUserGroupPageService;
    @Resource
    private FindMenuTreeService findMenuTreeService;
    /**
     * 打开用户组权限管理
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        //查找用户组信息
        request.setAttribute("userGroupList", findUserGroupPageService.findAll());
        List<EntityTree> entityTrees = findMenuTreeService.findMenuToTree();
        //查找所有菜单信息
        request.setAttribute("resourceTree", JSON.toJSONString(entityTrees));
        return "user/usergroupresource/manage";
    }
}
