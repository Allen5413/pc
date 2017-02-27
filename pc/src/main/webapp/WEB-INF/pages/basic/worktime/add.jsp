<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="addForm" name="addForm" method="post">
  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >编号：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入编号" required id="add_code" name="code"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >名称：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="add_name" name="name"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >开始时间：</label></div>
    <div class="am-u-sm-4">
      <input type="text" id="add_beginTime" name="beginTime" class="Wdate" placeholder="选择开始时间" style="width: 175px; height: 28px;" onfocus="WdatePicker({readOnly:true, dateFmt:'HH:mm'})"/>
    </div>
    <div class="am-u-sm-5">*必填</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >结束时间：</label></div>
    <div class="am-u-sm-4">
      <input type="text" id="add_endTime" name="endTime" class="Wdate" placeholder="选择结束时间" style="width: 175px; height: 28px;" onfocus="WdatePicker({readOnly:true, dateFmt:'HH:mm'})"/>
    </div>
    <div class="am-u-sm-5">*必填</div>
  </div>
  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >顺序号：</label></div>
    <div class="am-u-sm-4">
      <input type="number" id="add_sno" name="sno" min="1" placeholder="输入序号" style="width: 175px; height: 28px;"/>
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>
</form>