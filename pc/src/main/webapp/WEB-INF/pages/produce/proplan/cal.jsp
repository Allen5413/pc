<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="calForm" name="calForm" method="post">
  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right" style="padding-top: 5px;"><label >开始日期：</label></div>
    <div class="am-u-sm-7">
      <input class="am-input-sm no-padding-right" type="text" placeholder="开始日期" class="Wdate"
             onfocus="WdatePicker({firstDayOfWeek:1})" required id="start" name="start"  />
    </div>
    <div class="am-u-sm-2 no-padding-right" style="padding-top: 5px;">*必填</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right" style="padding-top: 5px;"><label >结束日期：</label></div>
    <div class="am-u-sm-7">
      <input class="am-input-sm no-padding-right" type="text" placeholder="结束日期" class="Wdate"
             required id="end" name="end"  onfocus="WdatePicker({firstDayOfWeek:1})"/>
    </div>
    <div class="am-u-sm-2 no-padding-right" style="padding-top: 5px;">*必填</div>
  </div>
</form>