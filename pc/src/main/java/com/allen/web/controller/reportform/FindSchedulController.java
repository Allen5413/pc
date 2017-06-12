package com.allen.web.controller.reportform;

import com.allen.entity.pojo.workgroup.WorkGroupForSchedulBean;
import com.allen.service.basic.workcore.FindWorkCoreForAllService;
import com.allen.service.basic.workgroup.FindWorkGroupForAllService;
import com.allen.service.reportform.FindWorkGroupSchedulService;
import com.allen.util.DateUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Allen on 2017/3/21.
 */
@Controller
@RequestMapping(value = "/findSchedul")
public class FindSchedulController extends BaseController {
    @Resource
    private FindWorkGroupSchedulService findWorkGroupSchedulService;
    @Resource
    private FindWorkGroupForAllService findWorkGroupForAllService;
    @Resource
    private FindWorkCoreForAllService findWorkCoreForAllService;

    @RequestMapping(value = "open")
    public String open(HttpServletRequest request) throws Exception {
        request.setAttribute("wgList", findWorkGroupForAllService.find());
        request.setAttribute("wcList", findWorkCoreForAllService.find());
        return "/reportform/schedul";
    }

    @RequestMapping(value = "find")
    public String find(@RequestParam(value="startDate") String startDate,
                       @RequestParam(value="endDate") String endDate,
                       @RequestParam(value="wgId", required=false, defaultValue = "") String wgId,
                       @RequestParam(value="wcCode", required=false, defaultValue = "") String wcCode,
                       @RequestParam(value="name", required=false, defaultValue = "") String name,
                       HttpServletRequest request) throws Exception {
        List<WorkGroupForSchedulBean> resultList = findWorkGroupSchedulService.find(startDate, endDate, wgId, name, wcCode);
        request.setAttribute("resultList", resultList);
        request.setAttribute("wgList", findWorkGroupForAllService.find());
        request.setAttribute("wcList", findWorkCoreForAllService.find());

        //循环查询时间
        Date start = DateUtil.getFormatDate(startDate, DateUtil.shortDatePattern);
        Date end = DateUtil.getFormatDate(endDate, DateUtil.shortDatePattern);
        long startTime = start.getTime();
        long endTime = end.getTime();
        long day = (endTime-startTime)/1000/60/60/24;
        if(day > 0){
            Map<String, String> dateMap = new LinkedHashMap<String, String>((int)day);
            String date = startDate;
            for(int i=0; i<=day; i++){
                dateMap.put(date, DateUtil.getThisWeek(date));
                date = DateUtil.afterDay(date);
            }
            request.setAttribute("dateMap", dateMap);
        }
        return "/reportform/schedul";
    }
}
