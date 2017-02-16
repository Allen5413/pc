package com.allen.web.controller.basic.customer;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.customer.DelCustomerService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/delCustomer")
public class DelCustomerController extends BaseController {

    @Resource
    private DelCustomerService delCustomerService;

    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id") long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delCustomerService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
