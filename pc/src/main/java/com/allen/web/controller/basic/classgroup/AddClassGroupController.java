package com.allen.web.controller.basic.classgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ClassGroup;
import com.allen.service.basic.classgroup.AddClassGroupService;
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
@RequestMapping("/addClassGroup")
public class AddClassGroupController extends BaseController {

    @Resource
    private AddClassGroupService addClassGroupService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/classgroup/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, ClassGroup classGroup) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != classGroup) {
            classGroup.setCreator(UserUtil.getLoginUserForName(request));
            classGroup.setOperator(UserUtil.getLoginUserForName(request));
            addClassGroupService.add(classGroup);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
