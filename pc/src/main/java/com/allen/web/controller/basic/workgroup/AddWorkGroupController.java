package com.allen.web.controller.basic.workgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.workgroup.AddWorkGroupService;
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
@RequestMapping("/addWorkGroup")
public class AddWorkGroupController extends BaseController {

    @Resource
    private AddWorkGroupService addWorkGroupService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/workgroup/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, WorkGroup workGroup) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != workGroup) {
            workGroup.setCreator(UserUtil.getLoginUserForName(request));
            workGroup.setOperator(UserUtil.getLoginUserForName(request));
            addWorkGroupService.add(workGroup);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
