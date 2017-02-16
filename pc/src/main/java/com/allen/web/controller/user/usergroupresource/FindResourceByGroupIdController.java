package com.allen.web.controller.user.usergroupresource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.UserGroupResource;
import com.allen.service.basic.menu.FindMenuTreeService;
import com.allen.service.user.usergroup.FindAllUserGroupService;
import com.allen.service.user.usergroupresource.FindResourceByGroupIdService;
import com.allen.util.EntityTree;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/2/15.
 */
@Controller
@RequestMapping(value = "/findResourceByGroupId")
public class FindResourceByGroupIdController {
    @Resource
    private FindResourceByGroupIdService findResourceByGroupIdService;
    /**
     * 保存角色资源信息
     * @param request
     * @return
     */
    @RequestMapping(value = "find")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, long userGroupId)throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",findResourceByGroupIdService.findResourceByGroupId(userGroupId));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
