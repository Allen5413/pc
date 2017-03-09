package com.allen.web.controller.basic.producelinecoreproductcg;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ClassGroup;
import com.allen.entity.basic.WorkMode;
import com.allen.service.basic.classgroup.FindClassGroupForAllService;
import com.allen.service.basic.producelinecoreproductcg.AddPlcpcgService;
import com.allen.service.basic.producelinecoreproductcg.FindPlcpcgByPlcpIdService;
import com.allen.service.basic.workmode.FindWorkModeForAllService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addPlcpcg")
public class AddPlcpcgController extends BaseController {

    @Resource
    private AddPlcpcgService addPlcpcgService;
    @Resource
    private FindClassGroupForAllService findClassGroupForAllService;
    @Resource
    private FindWorkModeForAllService findWorkModeForAllService;
    @Resource
    private FindPlcpcgByPlcpIdService findPlcpcgByPlcpIdService;

    /**
     * 打开新增页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request,
                       @RequestParam("plcpId")Long plcpId) throws Exception {
        //查询所有班组信息
        List<ClassGroup> classGroupList = findClassGroupForAllService.find();
        //查询所有工作模式信息
        List<WorkMode> workModeList = findWorkModeForAllService.find();
        //查询已经关联的班组信息
        List<Map> withCgList = findPlcpcgByPlcpIdService.find(plcpId);

        request.setAttribute("classGroupList", classGroupList);
        request.setAttribute("workModeList", workModeList);
        request.setAttribute("withCgList", withCgList);
        return "basic/producelinecoreproductcg/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request,
                          @RequestParam("plcpId")long plcpId,
                          @RequestParam(value = "delPlcpcgIds", required = false, defaultValue = "")String delPlcpcgIds,
                          @RequestParam(value = "plcpcgIds", required = false)Long[] plcpcgIds,
                          @RequestParam(value = "snos", required = false)Integer[] snos,
                          @RequestParam(value = "cgIds", required = false)Long[] cgIds,
                          @RequestParam(value = "wmIds", required = false)Long[] wmIds,
                          @RequestParam(value = "unitTimeCapacitys", required = false)Float[] unitTimeCapacitys,
                          @RequestParam(value = "minBatchs", required = false)Integer[] minBatchs) throws Exception {
        JSONObject jsonObject = new JSONObject();
        addPlcpcgService.add(plcpId, delPlcpcgIds, plcpcgIds, snos, cgIds, wmIds,
                unitTimeCapacitys, minBatchs, UserUtil.getLoginUserForName(request));
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
