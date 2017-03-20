package com.allen.web.controller.produce.proplan;

import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 包路径：com.allen.web.controller.produce.proplan
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-19 17:04
 */
@Controller
@RequestMapping(value = "/findProPlan")
public class FindProPlanController extends BaseController {

    @RequestMapping(value = "find")
    public String find(){
        return "/produce/proplan/page";
    }
}
