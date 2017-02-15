package com.allen.web.controller.basic.workgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.workgroup.DelWorkGroupService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delWorkGroup")
public class DelWorkGroupController extends BaseController {

    @Resource
    private DelWorkGroupService delWorkGroupService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delWorkGroupService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
