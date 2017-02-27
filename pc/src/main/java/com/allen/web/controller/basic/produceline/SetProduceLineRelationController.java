package com.allen.web.controller.basic.produceline;

import com.allen.service.basic.produceline.FindProduceLineForAllService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 设置生产线的各种关联
 * Created by Allen on 2017/2/26 0026.
 */
@Controller
@RequestMapping(value = "/setProduceLineRelation")
public class SetProduceLineRelationController extends BaseController {

    @Resource
    private FindProduceLineForAllService findProduceLineForAllService;
    @Resource
    private FindProductTypeSelectService findProductTypeSelectService;

    @RequestMapping(value = "open")
    public String find(HttpServletRequest request) throws Exception {
        request.setAttribute("produceLineList", findProduceLineForAllService.find());
        request.setAttribute("productTypeList", findProductTypeSelectService.find());
        return "/basic/produceline/setRelation";
    }
}
