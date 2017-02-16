package com.allen.web.controller.basic.workgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkGroup;
import com.allen.service.basic.workgroup.EditWorkGroupService;
import com.allen.service.basic.workgroup.FindWorkGroupByIdService;
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
@RequestMapping(value = "/editWorkGroup")
public class EditWorkGroupController extends BaseController {

    @Resource
    private EditWorkGroupService editWorkGroupService;
    @Resource
    private FindWorkGroupByIdService findWorkGroupByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        WorkGroup workGroup = findWorkGroupByIdService.find(id);
        request.setAttribute("workGroup", workGroup);
        return "basic/workgroup/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, WorkGroup workGroup)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != workGroup) {
            workGroup.setOperator(UserUtil.getLoginUserForName(request));
            workGroup.setOperateTime(DateUtil.getLongNowTime());
            editWorkGroupService.edit(workGroup);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
