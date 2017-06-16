package com.allen.web.controller.reportform;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Menu;
import com.allen.entity.basic.ProductScheduling;
import com.allen.service.basic.menu.EditMenuService;
import com.allen.service.basic.menu.FindMenuByIdService;
import com.allen.service.basic.productscheduling.EditPSCapacityByIdService;
import com.allen.service.basic.productscheduling.FindPSByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
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
@RequestMapping(value = "/editSchedulCapacity")
public class EditSchedulCapacityController extends BaseController {

    @Resource
    private EditPSCapacityByIdService editPSCapacityByIdService;
    @Resource
    private FindPSByIdService findPSByIdService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String openEditMenuPage(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ProductScheduling productScheduling = findPSByIdService.findById(id);
        request.setAttribute("productScheduling", productScheduling);
        return "/reportform/editSchedulCapacity";
    }

    /**
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(@RequestParam("id") long id,
                             @RequestParam("count") BigDecimal count)throws Exception{
        JSONObject jsonObject = new JSONObject();
        editPSCapacityByIdService.edit(id, count);
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
