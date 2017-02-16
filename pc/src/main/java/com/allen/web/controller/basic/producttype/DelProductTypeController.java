package com.allen.web.controller.basic.producttype;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.producttype.DelProductTypeService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delProductType")
public class DelProductTypeController extends BaseController {

    @Resource
    private DelProductTypeService delProductTypeService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delProductTypeService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
