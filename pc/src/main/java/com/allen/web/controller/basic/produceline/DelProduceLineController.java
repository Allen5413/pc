package com.allen.web.controller.basic.produceline;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.produceline.DelProduceLineService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delProduceLine")
public class DelProduceLineController extends BaseController {

    @Resource
    private DelProduceLineService delProduceLineService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delProduceLineService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
