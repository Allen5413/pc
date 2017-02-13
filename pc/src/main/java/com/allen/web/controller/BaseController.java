package com.allen.web.controller;

import com.allen.dao.PageInfo;
import com.allen.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2016/12/20.
 */
public class BaseController {

    protected PageInfo getPageInfo(HttpServletRequest request) {
        PageInfo pageinfo = new PageInfo();
        pageinfo.setCurrentPage(1);
        pageinfo.setCountOfCurrentPage(10);
        String page = request.getParameter("page");
        if(!StringUtil.isEmpty(page)) {
            pageinfo.setCurrentPage(Integer.parseInt(page));
        }
        String rows = request.getParameter("rows");
        if(!StringUtil.isEmpty(rows)) {
            pageinfo.setCountOfCurrentPage(Integer.parseInt(rows));
        }
        return pageinfo;
    }
}
