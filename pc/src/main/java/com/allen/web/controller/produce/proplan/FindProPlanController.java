package com.allen.web.controller.produce.proplan;

import com.allen.service.basic.productionplan.FindProductionPlanService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 包路径：com.allen.web.controller.produce.proplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-19 17:04
 */
@Controller
@RequestMapping(value = "/findProPlan")
public class FindProPlanController extends BaseController {

    @Resource
    private FindProductionPlanService findProductionPlanService;
    @RequestMapping(value = "find")
    public String find(HttpServletRequest request,String start,String end) throws Exception {
        if(StringUtil.isEmpty(start)||StringUtil.isEmpty(end)){
            return "/produce/proplan/page";
        }
        List<String> planCycle = new ArrayList<String>();
        String endNow = start;
        while (true){
            planCycle.add(endNow);
            endNow = DateUtil.afterDay(endNow);
            if(DateUtil.compareDate(end,endNow)<0){
                break;
            }
        }
        request.setAttribute("planCycle",planCycle);
        request.setAttribute("proPlanInfo",findProductionPlanService.find(null));
        return "/produce/proplan/page";
    }
}
