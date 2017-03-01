package com.allen.web.controller.basic.workmode;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkMode;
import com.allen.entity.basic.WorkModeTime;
import com.allen.service.basic.workmode.EditWorkModeService;
import com.allen.service.basic.workmode.FindWorkModeByIdService;
import com.allen.service.basic.workmodetime.FindWorkModeTimeByWorkModeIdService;
import com.allen.service.basic.worktime.FindWorkTimeAllService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editWorkMode")
public class EditWorkModeController extends BaseController {

    @Resource
    private EditWorkModeService editWorkModeService;
    @Resource
    private FindWorkModeByIdService findWorkModeByIdService;
    @Resource
    private FindWorkTimeAllService findWorkTimeAllService;
    @Resource
    private FindWorkModeTimeByWorkModeIdService findWorkModeTimeByWorkModeIdService;
    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        WorkMode workMode = findWorkModeByIdService.find(id);
        request.setAttribute("workMode", workMode);
        request.setAttribute("workTimes",findWorkTimeAllService.findAll());
        request.setAttribute("workModeTimes",findWorkModeTimeByWorkModeIdService.findWorkModeTimeByWorkModeId(id));
        return "basic/workmode/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, WorkMode workMode,String workTimeIds)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != workMode) {
            workMode.setOperator(UserUtil.getLoginUserForName(request));
            workMode.setOperateTime(DateUtil.getLongNowTime());
            if(!StringUtil.isEmpty(workTimeIds)){
                String[] workTimeIdArr = workTimeIds.split(",");
                List<WorkModeTime> workModeTimes = new ArrayList<WorkModeTime>();
                WorkModeTime workModeTime = null;
                for (String workTimeId:workTimeIdArr){
                    workModeTime = new WorkModeTime();
                    workModeTime.setCreator(workMode.getCreator());
                    workModeTime.setOperator(workMode.getOperator());
                    workModeTime.setWorkModeId(workMode.getId());
                    workModeTime.setWorkTimeId(Long.parseLong(workTimeId));
                    workModeTimes.add(workModeTime);
                }
                workMode.setWorkModeTimeList(workModeTimes);
            }
            editWorkModeService.edit(workMode);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
