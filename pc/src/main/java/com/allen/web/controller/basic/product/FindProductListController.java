package com.allen.web.controller.basic.product;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.PageInfo;
import com.allen.service.basic.product.FindProductPageService;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/2/27 0027.
 */
@Controller
@RequestMapping("/findProductList")
public class FindProductListController  extends BaseController {
    @Resource
    private FindProductPageService findProductPageService;

    @RequestMapping(value = "find")
    @ResponseBody
    public JSONObject find(@RequestParam(value="name", required=false) String name,
                       @RequestParam(value="code", required=false) String code,
                       @RequestParam(value="type",required =false) Integer type,
                       HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("a.FNUMBER", code);
        params.put("c.FNAME", new Object[]{StringUtil.isEmpty(name) ? "" : "%"+name+"%", "like"});
        params.put("b.FCATEGORYID", type);
        PageInfo pageInfo = super.getPageInfo(request);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("b.FENTRYID", true);
        pageInfo.setCountOfCurrentPage(99999);
        pageInfo = findProductPageService.find(pageInfo, params, sortMap);
        List<Map> productList = pageInfo.getPageResults();
        jsonObject.put("state", 0);
        jsonObject.put("productList", productList);
        return jsonObject;
    }
}
