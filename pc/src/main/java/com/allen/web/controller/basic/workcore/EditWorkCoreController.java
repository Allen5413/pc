package com.allen.web.controller.basic.workcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkCore;
import com.allen.service.basic.workcore.EditWorkCoreService;
import com.allen.service.basic.workcore.FindWorkCoreByIdService;
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
 * 修改工作中心
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editWorkCore")
public class EditWorkCoreController extends BaseController {

    @Resource
    private EditWorkCoreService editWorkCoreService;
    @Resource
    private FindWorkCoreByIdService findWorkCoreByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        WorkCore workCore = findWorkCoreByIdService.find(id);
        request.setAttribute("workCore", workCore);
        return "basic/workcore/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, WorkCore workCore)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != workCore) {
            workCore.setOperator(UserUtil.getLoginUserForName(request));
            workCore.setOperateTime(DateUtil.getLongNowTime());
            editWorkCoreService.edit(workCore);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
