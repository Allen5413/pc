package com.allen.web.controller.basic.customer;

import com.allen.dao.PageInfo;
import com.allen.service.basic.customer.FindCustomerPageService;
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
 * Created by Allen on 2016/12/20.
 */
@Controller
@RequestMapping(value = "/findCustomerPage")
public class FindCustoemrPageController extends BaseController {
    @Resource
    private FindCustomerPageService findCustomerPageService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="name", required=false) String name,
                       @RequestParam(value="code", required=false) String code,
                                  HttpServletRequest request) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("c.FNUMBER", code);
        params.put("cl.FNAME", new Object[]{StringUtil.isEmpty(name) ? "" : "%"+name+"%", "like"});
        PageInfo pageInfo = super.getPageInfo(request);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("c.FNUMBER", true);
        pageInfo = findCustomerPageService.find(pageInfo, params, sortMap);
        request.setAttribute("pageInfo", pageInfo);
        return "/basic/customer/page";
    }
}
