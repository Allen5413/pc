package com.allen.web.controller.basic.producelinecoreproductcg;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.producelinecoreproductcg.FindPlcpcgByPlcpIdService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/9.
 */
@Controller
@RequestMapping("/findPlcpcgByPlcpId")
public class FindPlcpcgByPlcpIdController extends BaseController {
    @Resource
    private FindPlcpcgByPlcpIdService findPlcpcgByPlcpIdService;

    @RequestMapping(value = "find")
    @ResponseBody
    public JSONObject find(@RequestParam("plcpId") long plcpId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<Map> list = findPlcpcgByPlcpIdService.find(plcpId);
        jsonObject.put("state", 0);
        jsonObject.put("list", list);
        return jsonObject;
    }
}
