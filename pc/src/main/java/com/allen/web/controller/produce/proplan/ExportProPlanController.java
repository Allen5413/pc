package com.allen.web.controller.produce.proplan;

import com.allen.entity.basic.ProduceLineUse;
import com.allen.entity.basic.ProductionPlan;
import com.allen.service.basic.productionplan.FindProductionPlanService;
import com.allen.service.basic.producttype.FindProductTypeSelectService;
import com.allen.util.DateUtil;
import com.allen.util.ExportUtil;
import com.allen.util.StringUtil;
import com.allen.web.controller.BaseController;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.allen.web.controller.produce.proplan
 * 功能说明：导出生产计划
 * 创建人： ly
 * 创建时间: 2017-03-19 17:04
 */
@Controller
@RequestMapping(value = "/exportProPlan")
public class ExportProPlanController extends BaseController {
    @Resource
    private FindProductionPlanService findProductionPlanService;
    @RequestMapping(value = "export")
    public String find(HttpServletRequest request,String start,String end,String name,
                       String type,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        if(StringUtil.isEmpty(start)||StringUtil.isEmpty(end)){
            return "/produce/proplan/page";
        }
        List<String> planCycle = new ArrayList<String>();
        String endNow = start;
        while (true){
            planCycle.add(endNow);
            endNow = DateUtil.afterDay(endNow);
            if(DateUtil.compareDate(end,endNow)<0){
                break;
            }
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("p.productionDateStart",DateUtil.getFormatDate(start,DateUtil.shortDatePattern));
        params.put("p.productionDateEnd",DateUtil.getFormatDate(end,DateUtil.shortDatePattern));
        params.put("p.productName", StringUtil.isEmpty(name) ? "" : "%"+name+"%");
        params.put("p.productType",type);
        request.setAttribute("planCycle",planCycle);
        ServletOutputStream outputStream = response.getOutputStream();
        String fileName = URLEncoder.encode("生产计划"+start+"到"+end, "UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
        //request.setAttribute("proPlanInfo",findProductionPlanService.find(params));
        // 创建一个workbook 对应一个excel应用文件
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = workBook.createSheet("生产计划");
        ExportUtil exportUtil = new ExportUtil(workBook, sheet);
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
        String[] titles = { "产品编码","产品名称", "净需求"};
        // 构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFRow headRow2 = sheet.createRow(1);
        XSSFCell cell = null;
        int i=0;
        for (i = 0; i < titles.length; i++){
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i,i));
        }
        for(String time:planCycle){
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(time);
            cell = headRow2.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue("需求");
            i++;
            headRow.createCell(i);
            cell = headRow2.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue("产能");
            i++;
            headRow.createCell(i);
            cell = headRow2.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue("计划");
            i++;
            sheet.addMergedRegion(new CellRangeAddress(0, 0,i-3,i-1));
        }
        // 构建表体数据
        try {
            List<ProductionPlan> productionPlans = findProductionPlanService.find(params);
            XSSFRow bodyRow = null;
            i=2;
            int cellIndex = 0;
            for(ProductionPlan productionPlan:productionPlans){
                bodyRow = sheet.createRow(i);

                cell = bodyRow.createCell(cellIndex);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(productionPlan.getProductNo());
                cellIndex++;

                cell = bodyRow.createCell(cellIndex);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(productionPlan.getProductName());
                cellIndex++;

                /*cell = bodyRow.createCell(cellIndex);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(productionPlan.getGrossNum()==null?"0":productionPlan.getGrossNum().toString());
                cellIndex++;*/

                cell = bodyRow.createCell(cellIndex);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(productionPlan.getPlanTotalNum()==null?"0":productionPlan.getPlanTotalNum().toString());
                cellIndex++;

               /* cell = bodyRow.createCell(cellIndex);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(productionPlan.getStockNum()==null?"0":productionPlan.getStockNum().toString());
                cellIndex++;*/

                for(String time:planCycle) {
                    cell = bodyRow.createCell(cellIndex);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(productionPlan.getPlans().get(time)==null?"":
                            productionPlan.getPlans().get(time).get("planNum").toString());
                    cellIndex++;

                    cell = bodyRow.createCell(cellIndex);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(productionPlan.getPlans().get(time)==null?"":
                            productionPlan.getPlans().get(time).get("capacity").toString());
                    cellIndex++;

                    cell = bodyRow.createCell(cellIndex);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(productionPlan.getPlans().get(time)==null?"":
                            productionPlan.getPlans().get(time).get("actualProductionNum").toString());
                    cellIndex++;
                }
                cellIndex=0;
                i++;
            }
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                outputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
