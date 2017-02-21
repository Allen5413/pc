package com.allen.web.controller.basic.produceline;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ProduceLine;
import com.allen.entity.pojo.workcore.WorkCoreBean;
import com.allen.service.basic.produceline.EditProduceLineService;
import com.allen.service.basic.produceline.FindProduceLineByIdService;
import com.allen.service.basic.workcore.FindWorkCoreAndPlIdByPlIdService;
import com.allen.util.DateUtil;
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
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editProduceLine")
public class EditProduceLineController extends BaseController {

    @Resource
    private EditProduceLineService editProduceLineService;
    @Resource
    private FindProduceLineByIdService findProduceLineByIdService;
    @Resource
    private FindWorkCoreAndPlIdByPlIdService findWorkCoreAndPlIdByPlIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ProduceLine produceLine = findProduceLineByIdService.find(id);
        //查询工作组关联的工作中心
        List<WorkCoreBean> wcList = findWorkCoreAndPlIdByPlIdService.findWith(id);
        request.setAttribute("produceLine", produceLine);
        request.setAttribute("wcList", wcList);
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
