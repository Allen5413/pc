package com.allen.web.controller.basic.producelinecoreproduct;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.AddProduceLineCoreProductService;
import com.allen.service.basic.producelinecoreproduct.SetProduceLineCoreProductService;
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
@RequestMapping("/setProduceLineCoreProduct")
public class SetProduceLineCoreProductController extends BaseController {

    @Resource
    private SetProduceLineCoreProductService setProduceLineCoreProductService;

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "set")
    @ResponseBody
    public JSONObject set(HttpServletRequest request,
                          @RequestParam("plId")long plId,
                          @RequestParam("wcId")long wcId,
                          @RequestParam("pIds")Long[] pIds,
                          @RequestParam("wmIds")Long[] wmIds,
                          @RequestParam("unitTimeCapacitys")Integer[] unitTimeCapacitys,
                          @RequestParam("qualifiedRates")Integer[] qualifiedRates,
                          @RequestParam("minBatchs")Integer[] minBatchs) throws Exception {
        JSONObject jsonObject = new JSONObject();
        setProduceLineCoreProductService.set(plId, wcId, pIds, wmIds, unitTimeCapacitys, qualifiedRates, minBatchs, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
