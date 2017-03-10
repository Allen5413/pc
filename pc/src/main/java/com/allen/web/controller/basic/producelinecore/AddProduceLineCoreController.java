package com.allen.web.controller.basic.producelinecore;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.WorkCore;
import com.allen.entity.pojo.workcore.WorkCoreBean;
import com.allen.service.basic.producelinecore.AddProduceLineCoreService;
import com.allen.service.basic.workcore.FindWorkCoreAndPlIdByPlIdService;
import com.allen.service.basic.workcore.FindWorkCoreForAllService;
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
@RequestMapping("/addProduceLineCore")
public class AddProduceLineCoreController extends BaseController {

    @Resource
    private AddProduceLineCoreService addProduceLineCoreService;
    @Resource
    private FindWorkCoreForAllService findWorkCoreForAllService;
    @Resource
    private FindWorkCoreAndPlIdByPlIdService findWorkCoreAndPlIdByPlIdService;

    /**
     * 打开新增页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("plId")Long plId) throws Exception {
        //查询所有中心信息
        List<WorkCore> workCoreList = findWorkCoreForAllService.find();

        //查询已经关联的中心信息
        List<WorkCoreBean> withWcList = findWorkCoreAndPlIdByPlIdService.findWith(plId);

        request.setAttribute("workCoreList", workCoreList);
        request.setAttribute("withWcList", withWcList);
        return "basic/producelinecore/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request,
                          @RequestParam("plId")long plId,
                          @RequestParam(value = "delPlcIds", required = false, defaultValue = "")String delPlcIds,
                          @RequestParam(value = "plcIds", required = false)Long[] plcIds,
                          @RequestParam(value = "wcIds", required = false)Long[] wcIds,
                          @RequestParam(value = "snos", required = false)Integer[] snos) throws Exception {
        JSONObject jsonObject = new JSONObject();
        addProduceLineCoreService.add(plId, delPlcIds, plcIds, wcIds, snos, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
