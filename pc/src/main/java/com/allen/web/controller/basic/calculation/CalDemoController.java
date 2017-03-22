package com.allen.web.controller.basic.calculation;

import com.alibaba.fastjson.JSONObject;
import com.allen.entity.basic.Menu;
import com.allen.entity.basic.PlanOrder;
import com.allen.entity.basic.Product;
import com.allen.service.basic.calculation.CalculationService;
import com.allen.service.basic.menu.AddMenuService;
import com.allen.service.basic.product.FindProductByPlanService;
import com.allen.util.UserUtil;
import com.allen.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能：计算产能
 */
@Controller
@RequestMapping("/cal")
public class CalDemoController extends BaseController {

    @Resource
    private FindProductByPlanService findProductByPlanService;
    @Resource
    private CalculationService calculationService;
    /**
     * 打开新增菜单页面
     * @return
     */
    @RequestMapping(value = "open")
    public String open(){
        try {
            calculationService.calculation("2017-03-01","2017-03-07");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "basic/menu/add";
    }

    /**
     * 功能：计算产品产能
     * @param product
     */
    private void productCapacity(Map product){
        Long productId = Long.valueOf(product.get("FMATERIALID").toString());
        /**第一步：查找产品对应的生产线
         *获取规则：
         * 1、先获取未加工产品的非公共生产线（联合查询当天生产线使用情况表）
         * *select * from produce_line a left join produce_line_use b on a.id = b.produce_line_id
        /*and b.production_date = '2017-03-06' /*生产日期为计划时间*/
        /*where a.product_ids like '%109251%' /*产品id* and a.is_public = 0 *非公共生产线*/
         /* 2、如果：如果没有未加工的非公共生产线，取未满负荷工作的生产线
         *select * from produce_line a left join produce_line_use b on a.id = b.produce_line_id and b.is_full = 0/**生产线未满负荷**/
        /*and b.production_date = '2017-03-06' /*生产日期为计划时间*/
        /*where a.product_ids like '%109251%' /*产品id* and a.is_public = 0 *非公共生产线*/
         /* 3、如果非公共生产线全部使用完毕了，就去找公共生产线
         */
        //select * from produce_line a where a.product_ids like '%109251%' and a.use_product_id=0 order by a.is_public
        //第二步：获取该生产线下的每个工作中心
        /*
        select c.qualified_rate,e.begin_time,e.end_time from produce_line_core b,produce_line_core_product c,work_mode_time d,work_time e
        where b.produce_line_id=1 and c.produce_line_core_id = b.id and c.product_id=109251
        and d.work_mode_id = c.work_mode_id and e.id = d.work_time_id order by e.sno
        */
    }
}
