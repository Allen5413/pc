<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="addForm" name="addForm" method="post">
  <div class="am-g am-margin-top">
    <div class="am-u-sm-2 am-text-right"><label >班次：</label></div>
    <div class="am-u-sm-6">
      <select class="am-input-sm" id="add_workTimes" name="workTimeIds" required multiple data-am-selected="{maxHeight: 140,btnWidth:'264px'}">
        <c:forEach items="${workTimes}" var="workTime">
          <option value="${workTime.id}">${workTime.name}</option>
        </c:forEach>
      </select>
    </div>
    <div class="am-u-sm-4">*必填，不可重复</div>
  </div>
  <div class="am-g am-margin-top">
    <div class="am-u-sm-2 am-text-right"><label >编号：</label></div>
    <div class="am-u-sm-6">
      <input class="am-input-sm" type="text" placeholder="输入编号" required id="add_code" name="code"  />
    </div>
    <div class="am-u-sm-4">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-2 am-text-right"><label >名称：</label></div>
    <div class="am-u-sm-6">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="add_name" name="name"  />
    </div>
    <div class="am-u-sm-4">*必填，不可重复</div>
  </div>

</form>
<script type="text/javascript">
  $(function(){
      $("select").selected();
  });
</script>