package com.allen.web.controller.basic.producelinecore;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.produceline.FindProduceLineAndWorkCoreIdByWorkCoreIdService;
import com.allen.service.basic.producelinecore.SetProduceLineCoreService;
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
@RequestMapping("/setProduceLineCoreForWcId")
public class SetProduceLineCoreForWcIdController extends BaseController {

    @Resource
    private FindProduceLineAndWorkCoreIdByWorkCoreIdService findProduceLineAndWorkCoreIdByWorkCoreIdService;
    @Resource
    private SetProduceLineCoreService setProduceLineCoreService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("wcId")long wcId)throws Exception{
        JSONObject json = findProduceLineAndWorkCoreIdByWorkCoreIdService.find(wcId);
        if(null != json && 0 < json.size()){
            List<JSONObject> withList = (List<JSONObject>) json.get("withList");
            List<JSONObject> notWithList = (List<JSONObject>) json.get("notWithList");
            request.setAttribute("withList", withList);
            request.setAttribute("notWithList", notWithList);
        }
        return "basic/producelinecore/setForCore";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "set")
    @ResponseBody
    public JSONObject set(HttpServletRequest request,
                          @RequestParam("wcId")long wcId,
                          @RequestParam(value = "plIds", required = false)Long[] plIds) throws Exception {
        JSONObject jsonObject = new JSONObject();
        setProduceLineCoreService.setForCore(wcId, plIds, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
