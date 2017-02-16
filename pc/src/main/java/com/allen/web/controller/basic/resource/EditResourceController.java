package com.allen.web.controller.basic.resource;

import com.alibaba.fastjson.JSONObject;
import com.allen.service.basic.resource.EditResourceService;
import com.allen.service.basic.resource.FindResourceByIdService;
import com.allen.service.basic.resource.FindResourceByParentIdService;
import com.allen.util.DateUtil;
import com.allen.util.StringUtil;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allen.entity.basic.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private FindResourceByParentIdService findResourceByParentIdService;
    /**
     * 打开编辑页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(@RequestParam("id") long id, HttpServletRequest request) throws Exception {
        Resource resource = findResourceByIdService.find(id);
        request.setAttribute("resource", resource);
        if(null != resource){
            request.setAttribute("buttons",findResourceByParentIdService.findButtonResource(resource.getId()));
        }
        return "basic/resource/edit";
    }

    /**
     * 编辑
     * @param request
     * @return
     */
    @RequestMapping(value = "editor")
    @ResponseBody
    public JSONObject editor(HttpServletRequest request, Resource resource,String buttons)throws Exception{
        JSONObject jsonObject = new JSONObject();
        if(null != resource) {
            resource.setOperator(UserUtil.getLoginUserForName(request));
            resource.setOperateTime(DateUtil.getLongNowTime());
            List<Resource> buttonList = buttonList = new ArrayList<Resource>();
            if(!StringUtil.isEmpty(buttons)){//获取资源按钮信息
                String[] buttonArr = buttons.split("!!");
                Resource buttonResource = null;
                for (String str:buttonArr){
                    buttonResource = new Resource();
                    String[] buttonInfo = str.split("!_!");
                    buttonResource.setOperator(UserUtil.getLoginUserForName(request));
                    buttonResource.setCreator(UserUtil.getLoginUserForName(request));
                    buttonResource.setOperateTime(DateUtil.getLongNowTime());
                    buttonResource.setName(buttonInfo[0]);
                    buttonResource.setButtonCode(buttonInfo[1]);
                    buttonResource.setId(Long.parseLong(buttonInfo[2]));
                    buttonResource.setParentId(resource.getId());
                    buttonResource.setIsButton(Resource.BUTTON);
                    buttonList.add(buttonResource);
                }
            }
            editResourceService.edit(resource,buttonList);
        }
        jsonObject.put("state", 0);
        return jsonObject;
    }
}
