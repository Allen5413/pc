package com.allen.web.controller.basic.workmode;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.workmode.DelWorkModeService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delWorkMode")
public class DelWorkModeController extends BaseController {

    @Resource
    private DelWorkModeService delWorkModeService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delWorkModeService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
