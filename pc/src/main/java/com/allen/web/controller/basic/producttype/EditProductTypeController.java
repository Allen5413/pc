package com.allen.web.controller.basic.producttype;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProductType;
import com.allen.service.basic.producttype.EditProductTypeService;
import com.allen.service.basic.producttype.FindProductTypeByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editProductType")
public class EditProductTypeController extends BaseController {

    @Resource
    private EditProductTypeService editProductTypeService;
    @Resource
    private FindProductTypeByIdService findProductTypeByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String openEditMenuPage(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ProductType productType = findProductTypeByIdService.find(id);
        request.setAttribute("productType", productType);
        return "basic/producttype/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, ProductType productType)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != productType) {
            productType.setOperator(UserUtil.getLoginUserForName(request));
            productType.setOperateTime(DateUtil.getLongNowTime());
            editProductTypeService.edit(productType);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
