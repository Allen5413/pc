package com.allen.web.controller.user.user;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.user.User;
import com.allen.service.user.user.AddUserService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 新增用户
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/addUser")
public class AddUserController extends BaseController {

    private static Logger log = Logger.getLogger(AddUserController.class);

    @Autowired
    private AddUserService addUserService;

    /**
     * 打开新增用户页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        return "user/user/add";
    }

    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, User user)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != user) {
            user.setCreator(UserUtil.getLoginUserForName(request));
            user.setOperator(UserUtil.getLoginUserForName(request));
            addUserService.add(user);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
