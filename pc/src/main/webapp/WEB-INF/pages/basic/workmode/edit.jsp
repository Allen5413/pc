<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editForm" method="post">
  <input type="hidden" name="id" value="${workMode.id}" />
  <div class="am-g am-margin-top">
    <div class="am-u-sm-2 am-text-right"><label >班次：</label></div>
    <div class="am-u-sm-6">
      <select class="am-input-sm" id="edit_workTimes" name="workTimeIds" required multiple data-am-selected="{maxHeight: 140,btnWidth:'264px'}">
        <c:forEach items="${workTimes}" var="workTime">
          <option value="${workTime.id}" <c:if test="${workModeTimes[workTime.id]==true}"> selected="selected" </c:if> >${workTime.name}</option>
        </c:forEach>
      </select>
    </div>
    <div class="am-u-sm-4">*必填，不可重复</div>
  </div>
  <div class="am-g am-margin-top">
    <div class="am-u-sm-2 am-text-right"><label >编号：</label></div>
    <div class="am-u-sm-6">
      <input class="am-input-sm" type="text" placeholder="输入编号" required id="edit_code" name="code" value="${workMode.code}"  />
    </div>
    <div class="am-u-sm-4">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-2 am-text-right"><label >名称：</label></div>
    <div class="am-u-sm-6">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="edit_name" name="name" value="${workMode.name}"  />
    </div>
    <div class="am-u-sm-4">*必填，不可重复</div>
  </div>

</form>
<script type="text/javascript">
  $(function(){
      $("select").selected();
  });
</script>