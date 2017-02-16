package com.allen.web.controller.basic.produceline;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProduceLine;
import com.allen.service.basic.produceline.AddProduceLineService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/22 0022.
 */
@Controller
@RequestMapping("/addProduceLine")
public class AddProduceLineController extends BaseController {

    @Resource
    private AddProduceLineService addProduceLineService;

    /**
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        return "basic/produceline/add";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, ProduceLine produceLine) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if(null != produceLine) {
            produceLine.setCreator(UserUtil.getLoginUserForName(request));
            produceLine.setOperator(UserUtil.getLoginUserForName(request));
            addProduceLineService.add(produceLine);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
