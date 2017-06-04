package com.allen.service.basic.producelineuse.impl;

import com.allen.dao.basic.producelineuse.FindProduceLineUseDao;
import com.allen.dao.basic.productscheduling.ProductSchedulingDao;
import com.allen.entity.basic.ProductScheduling;
import com.allen.entity.basic.WorkTime;
import com.allen.service.basic.producelineuse.SplitProduceLineUseService;
import com.allen.service.basic.productscheduling.AddProductSchedulingService;
import com.allen.service.basic.worktime.FindWorkTimeAllService;
import com.allen.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 包路径：com.allen.service.basic.producelineuse.impl
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-06-01 23:04
 */
@Service("splitProduceLineUseService")
public class SplitProduceLineUseServiceImpl implements SplitProduceLineUseService{
    @Resource
    private FindProduceLineUseDao findProduceLineUseDao;
    @Resource
    private FindWorkTimeAllService findWorkTimeAllService;
    @Resource
    private ProductSchedulingDao productSchedulingDao;
    @Transactional
    @Override
    public void splitProduceLineUserService(Date start, Date end) throws Exception {
        List<WorkTime> workTimeList =  findWorkTimeAllService.findAll();
       if(workTimeList==null&&workTimeList.size()==0){
           return;
       }
       Map<String,WorkTime> workTimeMap = new HashMap<String, WorkTime>();
       for(WorkTime workTime:workTimeList){
           workTimeMap.put(workTime.getSno()+"",workTime);
       }
       List<Map<String,Object>> produceLineUses =  findProduceLineUseDao.findProduceLineUseDetail(start,end);
       List<Map<String,Object>> newScheduling = new ArrayList<Map<String, Object>>();
       if(produceLineUses!=null&&produceLineUses.size()>0){
            BigDecimal workTime = null;//工作时长
            BigDecimal wortStart = null;//工作开始时间
            BigDecimal capacity = null;//生产数量
            BigDecimal hourCapacity = null;//单位时间产能
            int workSno = 0;//默认班次序号
            WorkTime startWorkTime = null;//默认上班
            String productionDate = null;
            String startTime = null;//实际上班开始时间
            String endTime = null;//实际上班结束时间
            BigDecimal totalTime = null;//总工作时间
            int startDivideInt = 0;//开始时间取整
            float startRemainder = 0;//开始时间取余
            int workDivideInt = 0;//工作时间取整数
            float workRemainder = 0;//工作时间取余
            int totalDivideInt = 0;//总工作时间取整数
            float totalRemainder = 0;//总工作时间取余
            BigDecimal eightHour = new BigDecimal(8);
            BigDecimal sixteenHour = new BigDecimal(16);
            BigDecimal twentyFourHour = new BigDecimal(24);
            String endWorkTime = null;
            for(Map<String,Object> produceLineUseMap:produceLineUses){
                wortStart = new BigDecimal(produceLineUseMap.get("work_start").toString());
                workTime = new BigDecimal(produceLineUseMap.get("work_time").toString());
                totalTime = workTime.add(wortStart);
                capacity = new BigDecimal(produceLineUseMap.get("capacity").toString());
                hourCapacity = capacity.divide(workTime,8,BigDecimal.ROUND_HALF_EVEN);
                workSno = Integer.parseInt(produceLineUseMap.get("sno").toString());
                productionDate = produceLineUseMap.get("production_date").toString().substring(0,10);
                startWorkTime = workTimeMap.get(workSno+"");
                startTime = DateUtil.calSecondNewDate(productionDate+" "+startWorkTime.getBeginTimeStr()+":00",
                        wortStart.multiply(new BigDecimal(3600)).intValue());
                endTime = DateUtil.calSecondNewDate(startTime,workTime.multiply(new BigDecimal(3600)).intValue());
                startDivideInt = wortStart.divideToIntegralValue(eightHour).intValue();//取整
                startRemainder = wortStart.divideAndRemainder(eightHour)[1]
                        .setScale(2,BigDecimal.ROUND_HALF_EVEN).floatValue();//取余
                workDivideInt = workTime.divideToIntegralValue(eightHour).intValue();//取整
                workRemainder = workTime.divideAndRemainder(eightHour)[1]
                        .setScale(2,BigDecimal.ROUND_HALF_EVEN).floatValue();//取余
                totalDivideInt = totalTime.divideToIntegralValue(eightHour).intValue();//取整
                totalRemainder = totalTime.divideAndRemainder(eightHour)[1]
                        .setScale(2,BigDecimal.ROUND_HALF_EVEN).floatValue();//取余
                if((startDivideInt<1&&workDivideInt<1&&totalDivideInt<1)
                        ||(startDivideInt<1&&workDivideInt==1&&workRemainder==0&&startRemainder==0)
                        ||(startDivideInt<1&&workDivideInt<1&&totalDivideInt==1&&totalRemainder==0)){//表示识在8小时以内的
                    produceLineUseMap.put("newCapacity",capacity);//生产数量
                    produceLineUseMap.put("start",startTime);//工作开始日期
                    produceLineUseMap.put("end",endTime);//工作结束日期
                    produceLineUseMap.put("workTime",workTime);
                    newScheduling.add(produceLineUseMap);
                }else if((startDivideInt<1&&workDivideInt<=1&&totalDivideInt==1)||
                        (startDivideInt<1&&workDivideInt==2&&workRemainder==0&&startRemainder==0)){//在第一个班次中加工了一部分时间 第二个班次未上满的情况
                    produceLineUseMap.put("workTime",eightHour.subtract(wortStart));
                    produceLineUseMap.put("newCapacity",hourCapacity.multiply(eightHour.subtract(wortStart))
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    produceLineUseMap.put("start",startTime);//工作开始日期
                    produceLineUseMap.put("end",productionDate+" "+startWorkTime.getEndTimeStr()+":00");//工作结束日期
                    newScheduling.add(produceLineUseMap);
                    workSno=workSno+1;//第二个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    HashMap<String,Object> newClassOne = new HashMap<String, Object>();
                    newClassOne.putAll(produceLineUseMap);
                    newClassOne.put("workTime",totalTime.subtract(eightHour));
                    newClassOne.put("wtName",startWorkTime.getName());
                    newClassOne.put("wtCode",startWorkTime.getCode());
                    newClassOne.put("newCapacity",hourCapacity.multiply(totalTime.subtract(eightHour))
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    newClassOne.put("start",productionDate+" "+startWorkTime.getBeginTimeStr()+":00");//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(newClassOne.get("start").toString(),
                            (totalTime.subtract(eightHour)).multiply(new BigDecimal(3600)).intValue());
                    newClassOne.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(newClassOne);
                } else if((startDivideInt<1&&workDivideInt<=2&&totalDivideInt==2)||
                        (startDivideInt<1&&workDivideInt==3)) {//在第一个班次中加工了一部分时间 第三个班次未上满的情况
                    produceLineUseMap.put("workTime",eightHour.subtract(wortStart));
                    produceLineUseMap.put("newCapacity",hourCapacity.multiply(eightHour.subtract(wortStart))
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    produceLineUseMap.put("start",startTime);//工作开始日期
                    produceLineUseMap.put("end",productionDate+" "+startWorkTime.getEndTimeStr()+":00");//工作结束日期
                    newScheduling.add(produceLineUseMap);
                    workSno=workSno+1;//第二个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    HashMap<String,Object> newClassOne = new HashMap<String, Object>();
                    newClassOne.putAll(produceLineUseMap);
                    newClassOne.put("workTime",eightHour);
                    newClassOne.put("wtName",startWorkTime.getName());
                    newClassOne.put("wtCode",startWorkTime.getCode());
                    newClassOne.put("newCapacity",hourCapacity.multiply(eightHour)
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    newClassOne.put("start", productionDate+" "+startWorkTime.getBeginTimeStr()+":00");//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(newClassOne.get("start").toString(),
                            eightHour.multiply(new BigDecimal(3600)).intValue());
                    newClassOne.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(newClassOne);
                    workSno=workSno+1;//第三个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    HashMap<String,Object> newClassTwo = new HashMap<String, Object>();
                    newClassTwo.putAll(produceLineUseMap);
                    newClassTwo.put("wtCode",startWorkTime.getCode());
                    newClassTwo.put("workTime",totalTime.subtract(sixteenHour));
                    newClassTwo.put("wtName",startWorkTime.getName());
                    newClassTwo.put("newCapacity",hourCapacity.multiply(totalTime.subtract(sixteenHour))
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    newClassTwo.put("start",productionDate+" "+startWorkTime.getBeginTimeStr()+":00");//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(newClassOne.get("start").toString(),
                            (totalTime.subtract(sixteenHour)).multiply(new BigDecimal(3600)).intValue());
                    newClassTwo.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(newClassTwo);
                }else if(startDivideInt==1&&workDivideInt<=1&&totalDivideInt==1) {//开始时间在第二个班次  结束时间都在第二个班次中
                    workSno=workSno+1;//第二个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    produceLineUseMap.put("workTime",workTime);
                    produceLineUseMap.put("wtName",startWorkTime.getName());
                    produceLineUseMap.put("wtCode",startWorkTime.getCode());
                    produceLineUseMap.put("newCapacity",capacity);//生产数量 8小时减去已经上班的时间
                    produceLineUseMap.put("start",DateUtil.calSecondNewDate(productionDate+" "+startWorkTime.getBeginTimeStr()+":00",
                            (wortStart.subtract(eightHour)).multiply(new BigDecimal(3600)).intValue()));//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(produceLineUseMap.get("start").toString(),
                            workTime.multiply(new BigDecimal(3600)).intValue());
                    produceLineUseMap.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(produceLineUseMap);
                }else if(startDivideInt==1&&workDivideInt<=2&&totalDivideInt==2) {//开始时间在第二个班次  结束时间都在第三个班次中
                    workSno=workSno+1;//第二个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    produceLineUseMap.put("wtName",startWorkTime.getName());
                    produceLineUseMap.put("wtCode",startWorkTime.getCode());
                    produceLineUseMap.put("workTime",sixteenHour.subtract(wortStart));
                    produceLineUseMap.put("newCapacity",hourCapacity.multiply(sixteenHour.subtract(wortStart))
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    produceLineUseMap.put("start",DateUtil.calSecondNewDate(productionDate+" "+startWorkTime.getBeginTimeStr()+":00",
                            (wortStart.subtract(eightHour)).multiply(new BigDecimal(3600)).intValue()));//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(produceLineUseMap.get("start").toString(),
                            workTime.multiply(new BigDecimal(3600)).intValue());
                    produceLineUseMap.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(produceLineUseMap);
                    workSno=workSno+1;//第三个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    HashMap<String,Object> newClassTwo = new HashMap<String, Object>();
                    newClassTwo.putAll(produceLineUseMap);
                    newClassTwo.put("workTime",totalTime.subtract(sixteenHour));
                    newClassTwo.put("wtName",startWorkTime.getName());
                    newClassTwo.put("wtCode",startWorkTime.getCode());
                    newClassTwo.put("newCapacity",hourCapacity.multiply(totalTime.subtract(sixteenHour))
                            .setScale(0,BigDecimal.ROUND_HALF_UP));//生产数量 8小时减去已经上班的时间
                    newClassTwo.put("start",productionDate+" "+startWorkTime.getBeginTimeStr()+":00");//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(newClassTwo.get("start").toString(),
                            (totalTime.subtract(sixteenHour)).multiply(new BigDecimal(3600)).intValue());
                    newClassTwo.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(newClassTwo);
                }else{//开始时间在第三个班次  结束时间都在第三个班次中
                    workSno=workSno+2;//第三个班次开始时间
                    startWorkTime =  workTimeMap.get(workSno+"");
                    HashMap<String,Object> newClassTwo = new HashMap<String, Object>();
                    newClassTwo.putAll(produceLineUseMap);
                    newClassTwo.put("workTime",workTime);
                    newClassTwo.put("wtName",startWorkTime.getName());
                    newClassTwo.put("wtCode",startWorkTime.getCode());
                    newClassTwo.put("newCapacity",capacity);//生产数量
                    newClassTwo.put("start",DateUtil.calSecondNewDate(productionDate+" "+startWorkTime.getBeginTimeStr()+":00",
                            (wortStart.subtract(sixteenHour)).multiply(new BigDecimal(3600)).intValue()));//工作开始日期
                    endWorkTime = productionDate+" "+startWorkTime.getEndTimeStr()+":00";//实际工作结束日期
                    endTime =  DateUtil.calSecondNewDate(newClassTwo.get("start").toString(),
                            workTime.multiply(new BigDecimal(3600)).intValue());
                    newClassTwo.put("end",DateUtil.compareFullDate(endWorkTime,endTime)>=0?endTime:endWorkTime);//工作结束日期
                    newScheduling.add(newClassTwo);
                }
            }
       }
       List<ProductScheduling> productSchedulings = productSchedulingDao.findByProductionDateBetween(start,end);
        if(productSchedulings!=null&&productSchedulings.size()>0){
            productSchedulingDao.delete(productSchedulings);
        }
        for(Map<String,Object> mapObj:newScheduling){
            productSchedulingDao.save(ProductScheduling.mapToProductScheduling(mapObj));
        }
    }

    public static void main(String[] args) {
        BigDecimal a= new BigDecimal(7.99);
        BigDecimal b= new BigDecimal(8);
        System.out.println(a.divideAndRemainder(b)[1].setScale(2,BigDecimal.ROUND_HALF_EVEN).floatValue());
    }
}
