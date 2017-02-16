package com.allen.web.controller.user.usergroupresource;

import com.allen.service.user.usergroup.FindAllUserGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2017/2/15.
 */
@Controller
@RequestMapping(value = "/showUserGroupResource")
public class ShowUserGroupResourceController {
    @Resource
    private FindAllUserGroupService findUserGroupPageService;
    /**
     * 打开用户组权限管理
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        request.setAttribute("userGroupList", findUserGroupPageService.findAll());
        return "user/usergroupresource/manage";
    }
}
