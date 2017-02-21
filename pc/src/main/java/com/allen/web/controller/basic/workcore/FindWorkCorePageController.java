package com.allen.web.controller.basic.workcore;

import com.allen.dao.PageInfo;
import com.allen.entity.basic.ProduceLine;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.produceline.FindProduceLineForAllService;
import com.allen.service.basic.workcore.FindWorkCorePageService;
import com.allen.service.basic.workgroup.FindWorkGroupForAllService;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
@Controller
@RequestMapping(value = "/findWorkCorePage")
public class FindWorkCorePageController extends BaseController {
    @Resource
    private FindWorkCorePageService findWorkCorePageService;
    @Resource
    private FindWorkGroupForAllService findWorkGroupForAllService;
    @Resource
    private FindProduceLineForAllService findProduceLineForAllService;

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="name", required=false) String name,
                       @RequestParam(value="code", required=false) String code,
                       @RequestParam(value="isPublic", required=false) Integer isPublic,
                       @RequestParam(value="wgId", required=false) Long wgId,
                       @RequestParam(value="plId", required=false) Long plId,
                                  HttpServletRequest request) throws Exception {
        //获取所有工作组
        List<WorkGroup> workGroupList = findWorkGroupForAllService.find();
        //获取所有生产线
        List<ProduceLine> produceLineList = findProduceLineForAllService.find();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wc.code", code);
        params.put("wc.name", new Object[]{StringUtil.isEmpty(name) ? "" : "%"+name+"%", "like"});
        params.put("wc.isPublic", isPublic);
        params.put("wgc.workGroupId", wgId);
        params.put("plc.produceLineId", plId);
        PageInfo pageInfo = super.getPageInfo(request);
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        sortMap.put("wc.id", true);
        pageInfo = findWorkCorePageService.find(pageInfo, params, sortMap);

        request.setAttribute("workGroupList", workGroupList);
        request.setAttribute("produceLineList", produceLineList);
        request.setAttribute("pageInfo", pageInfo);
        return "/basic/workcore/page";
    }
}
