package com.allen.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.allen.base.exception.BusinessException;
import com.allen.entity.basic.Menu;
import com.allen.entity.basic.Resource;
import com.allen.entity.user.User;
import com.allen.service.basic.menu.FindMenuByIdService;
import com.allen.service.basic.resource.FindResourceByUserIdService;
import com.allen.service.user.user.LoginUserService;
import com.allen.util.DateUtil;
import com.allen.util.MD5Util;
import com.allen.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 系统登录
 * Created by Allen on 2016/12/12.
 */
@Controller
public class LoginController {

    @javax.annotation.Resource
    private LoginUserService loginUserService;
    @javax.annotation.Resource
    private FindResourceByUserIdService findResourceByUserIdService;
    @javax.annotation.Resource
    private FindMenuByIdService findMenuByIdService;

    @RequestMapping("/")
    public String login(){
        return "/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject userLogin(@RequestParam("loginName")String loginName,
                            @RequestParam("pwd")String pwd,
                            HttpServletRequest request)throws Exception{
        JSONObject jsonObject = new JSONObject();
        User user = loginUserService.login(loginName, MD5Util.MD5(pwd));
        if(null != user){
            this.setSession(request, user.getId(), user.getLoginName(), user.getName());
            jsonObject.put("state", 0);
        }else{
            throw new BusinessException("用户名密码不存在");
        }
        return jsonObject;
    }

    @RequestMapping("/openIndex")
    public String index(HttpServletRequest request){
        //获取当前年月日星期
        String year = DateUtil.getThisYear();
        String month = DateUtil.getThisMonth();
        String day = DateUtil.getThisDay();
        String week = DateUtil.getThisWeek();
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("day", day);
        request.setAttribute("week", week);
        request.setAttribute("loginName", UserUtil.getLoginUserForLoginName(request));
        request.setAttribute("name", UserUtil.getLoginUserForName(request));
        request.setAttribute("menu", UserUtil.getLoginUserForMenu(request));
        return "/index";
    }

    protected String setSession(HttpServletRequest request, long userId, String loginName, String name)throws Exception{
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("loginName", loginName);
        request.getSession().setAttribute("name", name);
        //得到用户拥有的菜单资源权限
        Map<String, List<Resource>> menuMap = this.getUserMenu(userId);
        request.getSession().setAttribute("menuMap", menuMap);
        return "success";
    }

    protected Map<String, List<Resource>> getUserMenu(long userId)throws Exception{
        Map<String, List<Resource>> menuResourceMap = new HashMap<String, List<Resource>>();
        //获取用户所关联的资源
        List<Resource> resourceList = findResourceByUserIdService.find(userId);
        if(null != resourceList && 0 < resourceList.size()) {
            for(Resource resource : resourceList) {
                //得到菜单
                Menu menu = findMenuByIdService.find(resource.getMenuId());
                List<Resource> resourceList2 = menuResourceMap.get(menu.getName());
                if (null == resourceList2) {
                    resourceList2 = new ArrayList<Resource>();
                }
                boolean isExists = false;
                for(Resource resource2 : resourceList2){
                    if(resource2.getId() == resource.getId()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    resourceList2.add(resource);
                    menuResourceMap.put(menu.getName(), resourceList2);
                }
            }
        }
        //排序
        Set<String> keys = menuResourceMap.keySet();
        for (String key:keys){
            List<Resource> resources = menuResourceMap.get(key);
            Collections.sort(resources,new Comparator<Resource>(){
                public int compare(Resource arg0, Resource arg1) {
                    return arg0.getSno()>=arg1.getSno()?1:-1;
                }
            });
        }
        return menuResourceMap;
    }
}














