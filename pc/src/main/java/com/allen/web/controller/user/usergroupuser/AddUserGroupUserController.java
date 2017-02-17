package com.allen.web.controller.user.usergroupuser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.UserGroupResource;
import com.allen.entity.user.UserGroupUser;
import com.allen.service.user.usergroup.FindAllUserGroupService;
import com.allen.service.user.usergroupresource.AddUserGroupResourceService;
import com.allen.service.user.usergroupuser.AddUserGroupUserService;
import com.allen.service.user.usergroupuser.FindUserGroupByUserIdService;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
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
@RequestMapping(value = "/addUserGroupUser")
public class AddUserGroupUserController {
    private static Logger log = Logger.getLogger(AddUserGroupUserController.class);
    @Autowired
    private AddUserGroupUserService addUserGroupUserService;
    @Autowired
    private FindAllUserGroupService findAllUserGroupService;
    @Autowired
    private FindUserGroupByUserIdService findUserGroupByUserIdService;
    /**
     * 打开新增角色用户界面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,long userId){
        //查询所有的用户组信息
        request.setAttribute("userGroups",findAllUserGroupService.findAll());
        //查询以后的用户组信息
        request.setAttribute("currentGroups", JSON.toJSONString(findUserGroupByUserIdService.findUserGroupByUserId(userId)));
        request.setAttribute("userId",userId);
        return "user/usergroupuser/addUserGroupUser";
    }
    /**
     * 保存用户角色信息
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, long userId,String userGroupIds)throws Exception{
        JSONObject jsonObject = new JSONObject();
        List<UserGroupUser> userGroupUsers = null;
        if(!StringUtil.isEmpty(userGroupIds)){
            userGroupUsers = new ArrayList<UserGroupUser>();
            UserGroupUser userGroupUser = null;
            String[] userGroupIdArr = userGroupIds.split(",");
            for(String userGroupId:userGroupIdArr){
                userGroupUser = new UserGroupUser();
                userGroupUser.setUserId(userId);
                userGroupUser.setUserGroupId(Long.parseLong(userGroupId));
                userGroupUser.setCreator(UserUtil.getLoginUserForName(request));
                userGroupUsers.add(userGroupUser);
            }
        }
        addUserGroupUserService.addUserGroupUser(userGroupUsers,userId);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
