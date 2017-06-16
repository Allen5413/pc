package com.allen.web.controller.reportform;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProductScheduling;
import com.allen.service.basic.productscheduling.EditPSWorkClassByIdService;
import com.allen.service.basic.productscheduling.FindPSByIdService;
import com.allen.service.basic.worktime.FindWorkTimeAllService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editSchedulWClass")
public class EditSchedulWorkClassController extends BaseController {

    @Resource
    private EditPSWorkClassByIdService editPSWorkClassByIdService;
    @Resource
    private FindPSByIdService findPSByIdService;
    @Resource
    private FindWorkTimeAllService findWorkTimeAllService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String openEditMenuPage(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ProductScheduling productScheduling = findPSByIdService.findById(id);
        request.setAttribute("productScheduling", productScheduling);
        request.setAttribute("workTimeList", findWorkTimeAllService.findAll());
        return "/reportform/editSchedulWClass";
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
        editPSWorkClassByIdService.edit(id, code, name);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
