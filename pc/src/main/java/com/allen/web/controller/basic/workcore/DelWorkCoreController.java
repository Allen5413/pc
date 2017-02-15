package com.allen.web.controller.basic.workcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.workcore.DelWorkCoreService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delWorkCore")
public class DelWorkCoreController extends BaseController {

    @Resource
    private DelWorkCoreService delWorkCoreService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delWorkCoreService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
