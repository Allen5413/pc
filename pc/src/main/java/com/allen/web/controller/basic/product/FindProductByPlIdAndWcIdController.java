package com.allen.web.controller.basic.product;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.pojo.product.ProductBean;
import com.allen.service.basic.product.FindProductByPlIdAndWcIdService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Controller
@RequestMapping("/findProductByPlIdAndWcId")
public class FindProductByPlIdAndWcIdController extends BaseController {
    @Resource
    private FindProductByPlIdAndWcIdService findProductByPlIdAndWcIdService;

    @RequestMapping(value = "find")
    @ResponseBody
    public JSONObject find(@RequestParam("plId") long plId,
                           @RequestParam("wcId") long wcId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("plc.produce_line_id", plId);
        params.put("plc.work_core_id", wcId);
        List<ProductBean> productList = findProductByPlIdAndWcIdService.find(params);
        jsonObject.put("state", 0);
        jsonObject.put("productList", productList);
        return jsonObject;
    }
}
