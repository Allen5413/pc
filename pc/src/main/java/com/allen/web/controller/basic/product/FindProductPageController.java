package com.allen.web.controller.basic.product;

import com.allen.dao.PageInfo;
import com.allen.service.basic.product.FindProductPageService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/2/21.
 */
@Controller
@RequestMapping("/findProductPage")
public class FindProductPageController extends BaseController {
    @Resource
    private FindProductTypeSelectService findProductTypeSelectService;
    @Resource
    private FindProductPageService findProductPageService;
    @RequestMapping(value = "find")
    public String find(@RequestParam(value="name", required=false) String name,
                       @RequestParam(value="code", required=false) String code,
                       @RequestParam(value="type",required =false,defaultValue ="-1") int type,
                       HttpServletRequest request) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("p.code", code);
        params.put("p.name", new Object[]{StringUtil.isEmpty(name) ? "" : "%"+name+"%", "like"});
        if(type!=-1){
            params.put("p.type", type);
        }
        PageInfo pageInfo = super.getPageInfo(request);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("p.id", true);
        pageInfo = findProductPageService.find(pageInfo, params, sortMap);
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("productTypes",findProductTypeSelectService.find());
        return "/basic/product/page";
    }
}