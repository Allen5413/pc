package com.allen.web.controller.basic.workcore;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.pojo.workcore.WorkCoreBean;
import com.allen.service.basic.workcore.FindWorkCoreAndPlIdByPlIdService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通过生产线id，查询关联的工作中心
 * Created by Allen on 2017/2/27 0027.
 */
@Controller
@RequestMapping(value = "/findWorkCoreByPlId")
public class FindWorkCoreByPlIdController extends BaseController {

    @Resource
    private FindWorkCoreAndPlIdByPlIdService findWorkCoreAndPlIdByPlIdService;

    @RequestMapping(value = "find")
    @ResponseBody
    public List<WorkCoreBean> find(@RequestParam("plId")long plId)throws Exception{
        JSONObject json = findWorkCoreAndPlIdByPlIdService.find(plId);
        if(null != json && 0 < json.size()){
            return (List<WorkCoreBean>) json.get("withList");
        }
        return null;
    }
}
