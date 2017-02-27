package com.allen.web.controller.basic.workmode;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkMode;
import com.allen.entity.basic.WorkModeTime;
import com.allen.service.basic.workmode.AddWorkModeService;
import com.allen.service.basic.worktime.FindWorkTimeAllService;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addWorkMode")
public class AddWorkModeController extends BaseController {

    @Resource
    private AddWorkModeService addWorkModeService;
    @Resource
    private FindWorkTimeAllService findWorkTimeAllService;
    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request) throws Exception{
        request.setAttribute("workTimes",findWorkTimeAllService.findAll());
        return "basic/workmode/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, WorkMode workMode,String workTimeIds) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != workMode) {
            workMode.setCreator(UserUtil.getLoginUserForName(request));
            workMode.setOperator(UserUtil.getLoginUserForName(request));
            if(!StringUtil.isEmpty(workTimeIds)){
                String[] workTimeIdArr = workTimeIds.split(",");
                List<WorkModeTime> workModeTimes = new ArrayList<WorkModeTime>();
                WorkModeTime workModeTime = null;
                for (String workTimeId:workTimeIdArr){
                    workModeTime = new WorkModeTime();
                    workModeTime.setCreator(workMode.getCreator());
                    workModeTime.setOperator(workMode.getOperator());
                    workModeTime.setWorkTimeId(Long.parseLong(workTimeId));
                    workModeTimes.add(workModeTime);
                }
                workMode.setWorkModeTimeList(workModeTimes);
            }
            addWorkModeService.add(workMode);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
