package com.allen.web.controller.basic.producelinecoreproduct;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.EditProduceLineCoreProductService;
import com.allen.service.basic.producelinecoreproduct.FindProduceLineCoreProductByIdService;
import com.allen.util.StringUtil;
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
@RequestMapping("/editProduceLineCoreProductForWmIdById")
public class EditProduceLineCoreProductForWmIdByIdController extends BaseController {

    @Resource
    private FindProduceLineCoreProductByIdService findProduceLineCoreProductByIdService;
    @Resource
    private EditProduceLineCoreProductService editProduceLineCoreProductService;

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject edit(HttpServletRequest request,
                          @RequestParam("id")long id,
                          @RequestParam(value = "wmId", required = false)String wmId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        ProduceLineCoreProduct produceLineCoreProduct = findProduceLineCoreProductByIdService.find(id);
        if(null != produceLineCoreProduct){
            editProduceLineCoreProductService.edit(produceLineCoreProduct, UserUtil.getLoginUserForName(request));
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
