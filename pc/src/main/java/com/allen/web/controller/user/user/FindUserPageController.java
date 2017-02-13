package com.allen.web.controller.user.user;

import com.allen.dao.PageInfo;
import com.allen.service.user.user.FindUserPageService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/20.
 */
@Controller
@RequestMapping(value = "/findUserPage")
public class FindUserPageController extends BaseController {
    @Resource
    private FindUserPageService findUserPageService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="name", required=false) String name,
                       @RequestParam(value="state", required=false) Integer state,
                                  HttpServletRequest request) throws Exception {
        PageInfo pageInfo = super.getPageInfo(request);
        pageInfo = findUserPageService.find(pageInfo, name, state);
        request.setAttribute("pageInfo", pageInfo);
        return "/user/user/page";
    }
}
