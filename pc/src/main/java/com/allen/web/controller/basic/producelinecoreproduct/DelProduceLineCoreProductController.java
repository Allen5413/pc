package com.allen.web.controller.basic.producelinecoreproduct;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.producelinecoreproduct.DelProduceLineCoreProductByIdService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/delProduceLineCoreProduct")
public class DelProduceLineCoreProductController extends BaseController {

    @Resource
    private DelProduceLineCoreProductByIdService delProduceLineCoreProductByIdService;

    /**
     * @return
     */
    @RequestMapping(value = "del")
    @ResponseBody
    public JSONObject del(@RequestParam("id")long id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        delProduceLineCoreProductByIdService.del(id);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
