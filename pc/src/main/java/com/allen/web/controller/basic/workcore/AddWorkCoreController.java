package com.allen.web.controller.basic.workcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.workcore.AddWorkCoreService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addWorkCore")
public class AddWorkCoreController extends BaseController {

    @Resource
    private AddWorkCoreService addWorkCoreService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/workcore/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, WorkCore workCore) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != workCore) {
            workCore.setCreator(UserUtil.getLoginUserForName(request));
            workCore.setOperator(UserUtil.getLoginUserForName(request));
            addWorkCoreService.add(workCore);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
