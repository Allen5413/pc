package com.allen.web.controller.basic.classgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.classgroup.DelClassGroupService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delClassGroup")
public class DelClassGroupController extends BaseController {

    @Resource
    private DelClassGroupService delClassGroupService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delClassGroupService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
