package com.allen.web.controller.basic.producelinecoreproduct;

import com.alibaba.fastjson.JSONObject;
import com.allen.dao.PageInfo;
import com.allen.entity.basic.ProduceLineCoreProduct;
import com.allen.service.basic.producelinecoreproduct.AddProduceLineCoreProductService;
import com.allen.service.basic.product.FindProductByPlIdAndWcIdService;
import com.allen.service.basic.product.FindProductPageService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addProduceLineCoreProduct")
public class AddProduceLineCoreProductController extends BaseController {

    @Resource
    private AddProduceLineCoreProductService addProduceLineCoreProductService;
    @Resource
    private FindProductPageService findProductPageService;
    @Resource
    private FindProductByPlIdAndWcIdService findProductByPlIdAndWcIdService;

    /**
     * 打开新增菜单页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("plId")Long plId,
                       @RequestParam("wcId")Long wcId) throws Exception {
        //查询所有产品信息
        PageInfo pageInfo = super.getPageInfo(request);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("b.FENTRYID", true);
        pageInfo.setCountOfCurrentPage(99999);
        pageInfo = findProductPageService.find(pageInfo, null, sortMap);
        List productList = pageInfo.getPageResults();

        //查询已经关联的产品信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("plc.produce_line_id", plId);
        params.put("plc.work_core_id", wcId);
        List<Map> withProductList = findProductByPlIdAndWcIdService.find(params);

        request.setAttribute("productList", productList);
        request.setAttribute("withProductList", withProductList);
        return "basic/producelinecoreproduct/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request,
                          @RequestParam("plId")long plId,
                          @RequestParam("wcId")long wcId,
                          @RequestParam(value = "delPlcpIds", required = false, defaultValue = "")String delPlcpIds,
                          @RequestParam(value = "plcpIds", required = false)Long[] plcpIds,
                          @RequestParam(value = "pIds", required = false)Long[] pIds,
                          @RequestParam(value = "qualifiedRates", required = false)Integer[] qualifiedRates) throws Exception {
        JSONObject jsonObject = new JSONObject();
        addProduceLineCoreProductService.add(plId, wcId, delPlcpIds, plcpIds, pIds, qualifiedRates, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
