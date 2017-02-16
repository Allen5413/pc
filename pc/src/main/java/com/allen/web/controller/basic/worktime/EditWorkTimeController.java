package com.allen.web.controller.basic.worktime;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.worktime.EditWorkTimeService;
import com.allen.service.basic.worktime.FindWorkTimeByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
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
@RequestMapping(value = "/editWorkTime")
public class EditWorkTimeController extends BaseController {

    @Resource
    private EditWorkTimeService editWorkTimeService;
    @Resource
    private FindWorkTimeByIdService findWorkTimeByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        WorkTime workTime = findWorkTimeByIdService.find(id);
        request.setAttribute("workTime", workTime);
        return "basic/worktime/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, WorkTime workTime)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != workTime) {
            workTime.setOperator(UserUtil.getLoginUserForName(request));
            workTime.setOperateTime(DateUtil.getLongNowTime());
            editWorkTimeService.edit(workTime);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
