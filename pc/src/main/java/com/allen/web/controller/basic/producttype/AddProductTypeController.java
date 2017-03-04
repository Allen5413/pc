package com.allen.web.controller.basic.producttype;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.AddProductTypeService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addProductType")
public class AddProductTypeController extends BaseController {

    @Resource
    private AddProductTypeService addProductTypeService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/producttype/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, ProductType productType) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != productType) {
            addProductTypeService.add(productType);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
