package com.allen.web.controller.user.user;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.User;
import com.allen.service.user.user.EditUserPwdService;
import com.allen.service.user.user.EditUserService;
import com.allen.service.user.user.FindUserByIdService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2015/5/25.
 */
@Controller
@RequestMapping(value = "/editUser")
public class EditUserController extends BaseController{

    @Resource
    private FindUserByIdService findUserByIdService;
    @Resource
    private EditUserService editUserService;

    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request){
        request.setAttribute("userInfo",findUserByIdService.findUserById(id));
        return "user/user/edit";
    }


    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject edit(User user,HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        editUserService.edit(user);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}