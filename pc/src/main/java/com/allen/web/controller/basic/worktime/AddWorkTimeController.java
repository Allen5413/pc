package com.allen.web.controller.basic.worktime;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.worktime.AddWorkTimeService;
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
@RequestMapping("/addWorkTime")
public class AddWorkTimeController extends BaseController {

    @Resource
    private AddWorkTimeService addWorkTimeService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/worktime/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, WorkTime workTime) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != workTime) {
            workTime.setCreator(UserUtil.getLoginUserForName(request));
            workTime.setOperator(UserUtil.getLoginUserForName(request));
            addWorkTimeService.add(workTime);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
