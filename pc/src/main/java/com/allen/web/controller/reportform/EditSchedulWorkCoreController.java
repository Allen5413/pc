package com.allen.web.controller.reportform;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProductScheduling;
import com.allen.service.basic.classgroup.FindClassGroupForAllService;
import com.allen.service.basic.productscheduling.EditPSCapacityByIdService;
import com.allen.service.basic.productscheduling.EditPSWorkClassByIdService;
import com.allen.service.basic.productscheduling.EditPSWorkCoreByIdService;
import com.allen.service.basic.productscheduling.FindPSByIdService;
import com.allen.service.basic.workcore.FindWorkCoreForAllService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editSchedulWC")
public class EditSchedulWorkCoreController extends BaseController {

    @Resource
    private EditPSWorkCoreByIdService editPSWorkCoreByIdService;
    @Resource
    private FindPSByIdService findPSByIdService;
    @Resource
    private FindClassGroupForAllService findClassGroupForAllService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String openEditMenuPage(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ProductScheduling productScheduling = findPSByIdService.findById(id);
        request.setAttribute("productScheduling", productScheduling);
        request.setAttribute("workCoreList", findClassGroupForAllService.find());
        return "/reportform/editSchedulWC";
    }

    /**
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(@RequestParam("id") long id,
                             @RequestParam("code") String code,
                             @RequestParam("name") String name)throws Exception{
        JSONObject jsonObject = new JSONObject();
        editPSWorkCoreByIdService.edit(id, code, name);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
