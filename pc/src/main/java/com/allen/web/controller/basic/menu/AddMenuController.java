package com.allen.web.controller.basic.menu;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Menu;
import com.allen.service.basic.menu.AddMenuService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addMenu")
public class AddMenuController extends BaseController {

    @Resource
    private AddMenuService addMenuService;

    /**
     * 打开新增菜单页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/menu/add";
    }

    /**
     * 新增菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Menu menu) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != menu) {
            menu.setCreator(UserUtil.getLoginUserForName(request));
            addMenuService.add(menu);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
