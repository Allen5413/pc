package com.allen.web.controller.user.usergroupresource;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.UserGroup;
import com.allen.entity.user.UserGroupResource;
import com.allen.service.user.usergroup.AddUserGroupService;
import com.allen.service.user.usergroupresource.AddUserGroupResourceService;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.user.user.AddUserController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：新增工作组信息
 * Created by lenovo on 2017/2/15.
 */
@Controller
@RequestMapping(value = "/addUserGroupResource")
public class AddUserGroupResourceController {
    private static Logger log = Logger.getLogger(AddUserGroupResourceController.class);
    @Autowired
    private AddUserGroupResourceService addUserGroupResourceService;
    /**
     * 保存角色资源信息
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, long userGroupId,String sourceIds)throws Exception{
        JSONObject jsonObject = new JSONObject();
        List<UserGroupResource> userGroupResources = null;
        if(!StringUtil.isEmpty(sourceIds)){
            userGroupResources = new ArrayList<UserGroupResource>();
            UserGroupResource userGroupResource = null;
            String[] sourceIdArr = sourceIds.split(",");
            for(String sourceId:sourceIdArr){
                userGroupResource = new UserGroupResource();
                userGroupResource.setResourceId(Long.parseLong(sourceId));
                userGroupResource.setUserGroupId(userGroupId);
                userGroupResource.setCreator(UserUtil.getLoginUserForName(request));
                userGroupResources.add(userGroupResource);
            }
        }
        addUserGroupResourceService.addUserGroupResource(userGroupResources,userGroupId);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
