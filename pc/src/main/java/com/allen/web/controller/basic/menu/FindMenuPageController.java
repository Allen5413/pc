package com.allen.web.controller.basic.menu;

import com.allen.dao.PageInfo;
import com.allen.service.basic.menu.FindMenuPageService;
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
@RequestMapping(value = "/findMenuPage")
public class FindMenuPageController extends BaseController {
    @Resource
    private FindMenuPageService findMenuPageService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="name", required=false, defaultValue="") String name,
                                  HttpServletRequest request) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", new Object[]{StringUtil.isEmpty(name) ? "" : "%"+name+"%","like"});
        PageInfo pageInfo = super.getPageInfo(request);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("id", true);
        pageInfo = findMenuPageService.find(pageInfo, params, sortMap);
        request.setAttribute("pageInfo", pageInfo);
        return "/basic/menu/page";
    }
}
