package com.allen.web.controller.basic.producelinecore;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.producelinecore.SetProduceLineCoreService;
import com.allen.service.basic.workcore.FindWorkCoreAndPlIdByPlIdService;
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
@RequestMapping("/setProduceLineCoreForPlId")
public class SetProduceLineCoreForPlIdController extends BaseController {

    @Resource
    private FindWorkCoreAndPlIdByPlIdService findWorkCoreAndPlIdByPlIdService;
    @Resource
    private SetProduceLineCoreService setProduceLineCoreService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("plId")long plId)throws Exception{
        JSONObject json = findWorkCoreAndPlIdByPlIdService.find(plId);
        if(null != json && 0 < json.size()){
            List<JSONObject> withList = (List<JSONObject>) json.get("withList");
            List<JSONObject> notWithList = (List<JSONObject>) json.get("notWithList");
            request.setAttribute("withList", withList);
            request.setAttribute("notWithList", notWithList);
        }
        return "basic/producelinecore/setForLine";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "set")
    @ResponseBody
    public JSONObject set(HttpServletRequest request,
                          @RequestParam("plId")long plId,
                          @RequestParam(value = "wcIds", required = false)Long[] wcIds) throws Exception {
        JSONObject jsonObject = new JSONObject();
        setProduceLineCoreService.setForLine(plId, wcIds, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
