package com.allen.web.controller.basic.product;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Product;
import com.allen.entity.basic.ProductSelfUse;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.product.EditProductService;
import com.allen.service.basic.product.FindProductByIdService;
import com.allen.service.basic.product.FindProductSelectService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.service.basic.workcore.EditWorkCoreService;
import com.allen.service.basic.workcore.FindWorkCoreByIdService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
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
 * 修改工作中心
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editProduct")
public class EditProductController extends BaseController {

    @Resource
    private EditProductService editProductService;
    @Resource
    private FindProductByIdService findProductByIdService;
    @Resource
    private FindProductTypeSelectService findProductTypeSelectService;
    @Resource
    private FindProductSelectService findProductSelectService;
    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id,@RequestParam("fsno") long fsno, HttpServletRequest request) throws Exception {
        request.setAttribute("id", id);
        request.setAttribute("fsno",fsno);
        return "basic/product/setSno";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, Product product)throws Exception{
        JSONObject jsonObject = new JSONObject();
        editProductService.edit(product);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
