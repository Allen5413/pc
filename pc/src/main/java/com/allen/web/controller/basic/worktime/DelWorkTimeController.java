package com.allen.web.controller.basic.worktime;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.worktime.DelWorkTimeService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delWorkTime")
public class DelWorkTimeController extends BaseController {

    @Resource
    private DelWorkTimeService delWorkTimeService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delWorkTimeService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
