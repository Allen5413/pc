package com.allen.base.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 * Created by Allen on 2016/12/7.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e)  {
        JSONObject jsonObject = new JSONObject();
        //打印异常信息：
        e.printStackTrace();
        jsonObject.put("state", 1);
        jsonObject.put("msg", "程序出现了异常");
        return jsonObject;
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public JSONObject businessErrorHandler(HttpServletRequest req, Exception e)  {
        JSONObject jsonObject = new JSONObject();
        //打印异常信息：
        e.printStackTrace();

        String eMsg = e.getMessage();
        if(-1 < eMsg.indexOf("StaleObjectStateException")){
            eMsg = "您操作的数据已经被修改，请重新获取最新的数据再做操作！";
        }
        jsonObject.put("state", 1);
        jsonObject.put("msg", eMsg);
        return jsonObject;
    }
}
