package com.allen.web.controller.user.user;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.User;
import com.allen.service.user.user.DelUserByIdService;
import com.allen.service.user.user.EditUserService;
import com.allen.service.user.user.FindUserByIdService;
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
@RequestMapping(value = "/delUser")
public class DelUserController extends BaseController{
    @Resource
    private DelUserByIdService delUserByIdService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delUserByIdService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}