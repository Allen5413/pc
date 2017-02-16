package com.allen.web.controller.basic.produceline;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProduceLine;
import com.allen.service.basic.produceline.EditProduceLineService;
import com.allen.service.basic.produceline.FindProduceLineByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editProduceLine")
public class EditProduceLineController extends BaseController {

    @Resource
    private EditProduceLineService editProduceLineService;
    @Resource
    private FindProduceLineByIdService findProduceLineByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ProduceLine produceLine = findProduceLineByIdService.find(id);
        request.setAttribute("produceLine", produceLine);
        return "basic/produceline/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, ProduceLine produceLine)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != produceLine) {
            produceLine.setOperator(UserUtil.getLoginUserForName(request));
            produceLine.setOperateTime(DateUtil.getLongNowTime());
            editProduceLineService.edit(produceLine);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
