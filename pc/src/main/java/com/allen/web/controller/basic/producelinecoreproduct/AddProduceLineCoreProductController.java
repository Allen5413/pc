package com.allen.web.controller.basic.producelinecoreproduct;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.AddProduceLineCoreProductService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addProduceLineCoreProduct")
public class AddProduceLineCoreProductController extends BaseController {

    @Resource
    private AddProduceLineCoreProductService addProduceLineCoreProductService;

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request,
                          @RequestParam("plId")long plId,
                          @RequestParam("wcId")long wcId,
                          @RequestParam("pId")long pId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        ProduceLineCoreProduct produceLineCoreProduct = addProduceLineCoreProductService.add(plId, wcId, pId, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        jsonObject.put("plcpId", produceLineCoreProduct.getId());
        return jsonObject;
    }
}
