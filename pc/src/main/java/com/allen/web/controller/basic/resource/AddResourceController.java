package com.allen.web.controller.basic.resource;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Resource;
import com.allen.service.basic.resource.AddResourceService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 新增资源
 * Created by Allen on 2015/4/28.
 */
@Controller
@RequestMapping(value = "/addResource")
public class AddResourceController extends BaseController {

    private static Logger log = Logger.getLogger(AddResourceController.class);

    @Autowired
    private AddResourceService addResourceService;

    /**
     * 打开新增资源页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(HttpServletRequest request){
        return "basic/resource/add";
    }

    /**
     * 新增资源
     * @param request
     * @return
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, Resource resource)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != resource) {
            resource.setCreator(UserUtil.getLoginUserForName(request));
            resource.setOperator(UserUtil.getLoginUserForName(request));
            addResourceService.add(resource);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
