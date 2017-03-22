package com.allen.web.controller.reportform;

import com.allen.entity.pojo.workgroup.WorkGroupForCapacityBean;
import com.allen.service.basic.classgroup.FindClassGroupForAllService;
import com.allen.service.basic.workcore.FindWorkCoreForAllService;
import com.allen.service.basic.workgroup.FindWorkGroupForAllService;
import com.allen.service.reportform.FindCapacityService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/3/21.
 */
@Controller
@RequestMapping(value = "/findCapacity")
public class FindCapacityController extends BaseController {
    @Resource
    private FindCapacityService findCapacityService;
    @Resource
    private FindWorkGroupForAllService findWorkGroupForAllService;
    @Resource
    private FindWorkCoreForAllService findWorkCoreForAllService;
    @Resource
    private FindClassGroupForAllService findClassGroupForAllService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="wgId", required=false, defaultValue = "") String wgId,
                       @RequestParam(value="wcId", required=false, defaultValue = "") String wcId,
                       @RequestParam(value="cgId", required=false, defaultValue = "") String cgId,
                       HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("wgId", wgId);
        params.put("wcId", wcId);
        params.put("cgId", cgId);
        List<WorkGroupForCapacityBean> resultList = findCapacityService.find(params);
        request.setAttribute("resultList", resultList);
        request.setAttribute("wgList", findWorkGroupForAllService.find());
        request.setAttribute("wcList", findWorkCoreForAllService.find());
        request.setAttribute("cgList", findClassGroupForAllService.find());
        return "/reportform/capacity";
    }
}
