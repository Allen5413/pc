package com.allen.web.controller.produce.proplan;

import com.allen.service.basic.productionplan.FindProductionPlanService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private FindProductTypeSelectService findProductTypeSelectService;
    @Resource
    private FindProductionPlanService findProductionPlanService;
    @RequestMapping(value = "find")
    public String find(HttpServletRequest request,String start,String end,String name,String type) throws Exception {
        request.setAttribute("productTypes",findProductTypeSelectService.find());
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
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("p.productionDateStart",DateUtil.getFormatDate(start,DateUtil.shortDatePattern));
        params.put("p.productionDateEnd",DateUtil.getFormatDate(end,DateUtil.shortDatePattern));
        params.put("p.productName", StringUtil.isEmpty(name) ? "" : "%"+name+"%");
        params.put("p.productType",type);
        request.setAttribute("planCycle",planCycle);
        request.setAttribute("proPlanInfo",findProductionPlanService.find(params));
        return "/produce/proplan/page";
    }
}
