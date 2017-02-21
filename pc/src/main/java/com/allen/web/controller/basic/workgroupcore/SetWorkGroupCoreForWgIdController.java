package com.allen.web.controller.basic.workgroupcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.pojo.workcore.WorkCoreBean;
import com.allen.service.basic.workcore.FindWorkCoreAndWorkGroupIdByWorkGroupIdService;
import com.allen.service.basic.workgroupcore.SetWorkGroupCoreService;
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
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/setWorkGroupCoreForWgId")
public class SetWorkGroupCoreForWgIdController extends BaseController {

    @Resource
    private FindWorkCoreAndWorkGroupIdByWorkGroupIdService findWorkCoreAndWorkGroupIdByWorkGroupIdService;
    @Resource
    private SetWorkGroupCoreService setWorkGroupCoreService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("wgId")long wgId)throws Exception{
        JSONObject json = findWorkCoreAndWorkGroupIdByWorkGroupIdService.find(wgId);
        if(null != json && 0 < json.size()){
            List<WorkCoreBean> withList = (List<WorkCoreBean>) json.get("withList");
            List<WorkCoreBean> notWithList = (List<WorkCoreBean>) json.get("notWithList");
            request.setAttribute("withList", withList);
            request.setAttribute("notWithList", notWithList);
        }
        return "basic/workgroupcore/setForGroup";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "set")
    @ResponseBody
    public JSONObject set(HttpServletRequest request,
                          @RequestParam("wgId")long wgId,
                          @RequestParam(value = "wcIds", required = false)Long[] wcIds) throws Exception {
        JSONObject jsonObject = new JSONObject();
        setWorkGroupCoreService.setForGroup(wgId, wcIds, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
