package com.allen.web.controller.basic.resource;

import com.allen.service.basic.resource.FindResourceByMenuIdService;
import com.allen.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.allen.entity.basic.Resource;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/findResourceByMenuId")
public class FindResourceByMenuIdController extends BaseController {

    @Autowired
    private FindResourceByMenuIdService findResourceByMenuIdService;

    @RequestMapping(value = "open")
    public String open(@RequestParam("menuId") long menuId,
                       HttpServletRequest request) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>(1);
        paramsMap.put("menuId", menuId);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>(1);
        sortMap.put("id", true);
        List<Resource> resourceList = findResourceByMenuIdService.find(paramsMap, sortMap);
        request.setAttribute("resourceList", resourceList);
        return "basic/resource/list";
    }
}
