package com.allen.web.controller.basic.classgroup;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.ClassGroup;
import com.allen.service.basic.classgroup.EditClassGroupService;
import com.allen.service.basic.classgroup.FindClassGroupByIdService;
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
@RequestMapping(value = "/editClassGroup")
public class EditClassGroupController extends BaseController {

    @Resource
    private EditClassGroupService editClassGroupService;
    @Resource
    private FindClassGroupByIdService findClassGroupByIdService;

    /**
     * 打开
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        ClassGroup classGroup = findClassGroupByIdService.find(id);
        request.setAttribute("classGroup", classGroup);
        return "basic/classgroup/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, ClassGroup classGroup)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != classGroup) {
            classGroup.setOperator(UserUtil.getLoginUserForName(request));
            classGroup.setOperateTime(DateUtil.getLongNowTime());
            editClassGroupService.edit(classGroup);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
