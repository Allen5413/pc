package com.allen.web.controller.user.usergroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.UserGroup;
import com.allen.service.user.usergroup.AddUserGroupService;
import com.allen.util.UserUtil;
import com.allen.web.controller.user.user.AddUserController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 功能：新增工作组信息
 * Created by lenovo on 2017/2/15.
 */
@Controller
@RequestMapping(value = "/addUserGroup")
public class AddUserGroupController {
    private static Logger log = Logger.getLogger(AddUserController.class);
    @Autowired
    private AddUserGroupService addUserGroupService;
    /**
     * 打开新增角色页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        return "user/usergroup/addUserGroup";
    }
    /**
     * 新添角色信息
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, UserGroup userGroup)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != userGroup) {
            userGroup.setCreator(UserUtil.getLoginUserForName(request));
            userGroup.setOperator(UserUtil.getLoginUserForName(request));
            jsonObject.put("data",addUserGroupService.addUserGroup(userGroup));
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
