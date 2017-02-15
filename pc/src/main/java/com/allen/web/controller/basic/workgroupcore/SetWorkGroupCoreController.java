package com.allen.web.controller.basic.workgroupcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.workgroup.FindWorkGroupAndWorkCoreIdByWorkCoreIdService;
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
@RequestMapping("/setWorkGroupCore")
public class SetWorkGroupCoreController extends BaseController {

    @Resource
    private FindWorkGroupAndWorkCoreIdByWorkCoreIdService findWorkGroupAndWorkCoreIdByWorkCoreIdService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("wcId")long wcId)throws Exception{
        JSONObject json = findWorkGroupAndWorkCoreIdByWorkCoreIdService.find(wcId);
        if(null != json && 0 < json.size()){
            List<JSONObject> withList = (List<JSONObject>) json.get("withList");
            List<JSONObject> notWithList = (List<JSONObject>) json.get("notWithList");
            request.setAttribute("withList", withList);
            request.setAttribute("notWithList", notWithList);
        }
        return "basic/workgroupcore/setForCore";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "set")
    @ResponseBody
    public JSONObject set(HttpServletRequest request,
                          @RequestParam("wcId")long wcId,
                          @RequestParam("wgIds")String wgIds) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("state", 0);
        return jsonObject;
    }
}
