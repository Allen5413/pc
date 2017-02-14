package com.allen.web.interceptor;

import com.allen.util.StringUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器1
 * Created by Allen on 2016/12/9.
 */
public class LoginInterceptor implements HandlerInterceptor {

    //不需要拦截的路径
    private static final String[] IGNORE_URI = {"/login", "/css"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 只有返回true才会继续向下执行，返回false取消当前请求
        boolean flag = false;
        String url = request.getRequestURL().toString();
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            if(url.lastIndexOf("/") == url.length()-1){
                flag = true;
            }else {
                String loginName = null == request.getSession().getAttribute("loginName") ? "" : request.getSession().getAttribute("loginName").toString();
                if (!StringUtil.isEmpty(loginName)) {
                    flag = true;
                } else {
                    response.sendRedirect("/");
                }
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
