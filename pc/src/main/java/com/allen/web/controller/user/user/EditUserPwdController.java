package com.allen.web.controller.user.user;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.user.user.EditUserPwdService;
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
@RequestMapping(value = "/editPwd")
public class EditUserPwdController extends BaseController{

    @Resource
    private EditUserPwdService editUserPwdService;

    @RequestMapping(value = "open")
    public String open(){
        return "user/user/editPwd";
    }


    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject edit(@RequestParam(value = "newPwd") String newPwd,
                              @RequestParam(value = "oldPwd") String oldPwd,
                                HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        editUserPwdService.edit(UserUtil.getLoginUserForLoginName(request), oldPwd, newPwd);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}