package com.allen.web.controller.basic.product;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductSelfUse;
import com.allen.entity.user.User;
import com.allen.service.basic.product.AddProductService;
import com.allen.service.basic.product.FindProductSelectService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addProduct")
public class AddProductController extends BaseController {

    @Resource
    private AddProductService addProductService;
    @Resource
    private FindProductTypeSelectService findProductTypeSelectService;
    @Resource
    private FindProductSelectService findProductSelectService;
    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request) throws Exception{
        //查找产品类别信息
        request.setAttribute("productTypes",findProductTypeSelectService.find());
        //获取包含产品信息
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("p.selfMade",1);
        request.setAttribute("products",findProductSelectService.find(params));
        return "basic/product/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Product product,String productSelfUseList) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != product) {
            if(!StringUtil.isEmpty(productSelfUseList)){
               product.setProductSelfUses(JSONObject.parseArray(productSelfUseList, ProductSelfUse.class));
            }
            addProductService.add(product);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
