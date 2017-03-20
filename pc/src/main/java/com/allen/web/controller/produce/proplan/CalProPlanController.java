package com.allen.web.controller.produce.proplan;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.calculation.CalculationService;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 包路径：com.allen.web.controller.produce.proplan
 * 功能说明：计算生产计划
 * 创建人： ly
 * 创建时间: 2017-03-19 17:04
 */
@Controller
@RequestMapping(value = "/calProPlan")
public class CalProPlanController extends BaseController {

    @Resource
    private CalculationService calculationService;
    private boolean isRun = false;
    @RequestMapping(value = "cal")
    @ResponseBody
    public JSONObject find(){
        JSONObject jsonObject = new JSONObject();
        if(isRun){
            jsonObject.put("state", 1);
            return jsonObject;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRun = true;
                try {
                    calculationService.calculation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isRun = false;
            }
        }).start();
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
