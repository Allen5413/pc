package com.allen.web.controller.basic.product;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductSelfUse;
import com.allen.service.basic.product.EditProductService;
import com.allen.service.basic.product.FindProductByIdService;
import com.allen.service.basic.product.FindProductSelectService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品详细信息
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/detailProduct")
public class DetailProductController extends BaseController {

    @Resource
    private FindProductByIdService findProductByIdService;
    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        request.setAttribute("productInfo", findProductByIdService.find(id));
        return "basic/product/detail";
    }

}
