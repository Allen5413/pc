package com.allen.web.controller.basic.menu;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Menu;
import com.allen.service.basic.menu.EditMenuService;
import com.allen.service.basic.menu.FindMenuByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 修改菜单
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editMenu")
public class EditMenuController extends BaseController {

    @Resource
    private EditMenuService editMenuService;
    @Resource
    private FindMenuByIdService findMenuByIdService;

    /**
     * 打开编辑菜单页面
     * @return
     */
    @RequestMapping(value = "open")
    public String openEditMenuPage(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        Menu menu = findMenuByIdService.find(id);
        request.setAttribute("menu", menu);
        return "basic/menu/edit";
    }

    /**
     * 编辑菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, Menu menu)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != menu) {
            menu.setOperator(UserUtil.getLoginUserForName(request));
            menu.setOperateTime(DateUtil.getLongNowTime());
            editMenuService.edit(menu);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
