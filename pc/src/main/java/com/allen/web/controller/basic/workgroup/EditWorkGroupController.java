package com.allen.web.controller.basic.workgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkGroup;
import com.allen.entity.pojo.workcore.WorkCoreBean;
import com.allen.service.basic.workcore.FindWorkCoreAndWorkGroupIdByWorkGroupIdService;
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
import java.util.List;

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
    @Resource
    private FindWorkCoreAndWorkGroupIdByWorkGroupIdService findWorkCoreAndWorkGroupIdByWorkGroupIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        WorkGroup workGroup = findWorkGroupByIdService.find(id);
        //查询工作组关联的工作中心
        List<WorkCoreBean> wcList = findWorkCoreAndWorkGroupIdByWorkGroupIdService.findWith(id);
        request.setAttribute("workGroup", workGroup);
        request.setAttribute("wcList", wcList);
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
