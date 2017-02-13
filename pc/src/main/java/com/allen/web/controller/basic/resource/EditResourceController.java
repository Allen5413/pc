package com.allen.web.controller.basic.resource;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.resource.EditResourceService;
import com.allen.service.basic.resource.FindResourceByIdService;
import com.allen.util.DateUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allen.entity.basic.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 修改菜单
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/editResource")
public class EditResourceController extends BaseController {

    @Autowired
    private EditResourceService editResourceService;
    @Autowired
    private FindResourceByIdService findResourceByIdService;

    /**
     * 打开编辑页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        Resource resource = findResourceByIdService.find(id);
        request.setAttribute("resource", resource);
        return "basic/resource/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, Resource resource)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != resource) {
            resource.setOperator(UserUtil.getLoginUserForName(request));
            resource.setOperateTime(DateUtil.getLongNowTime());
            editResourceService.edit(resource);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
