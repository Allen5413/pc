<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editForm" method="post">
  <input type="hidden" name="id" value="${productScheduling.id}" />
  <input type="hidden" id="name" name="name" />
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right">班次：</label>
    <div class="am-u-sm-6">
      <select id="code" name="code" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
        <option value=""></option>
        <option value="null">全部</option>
        <c:forEach var="workTime" items="${workTimeList}">
          <option value="${workTime.code}" <c:if test="${productScheduling.workClassCode eq workTime.code}">selected="selected" </c:if> >${workTime.name}</option>
        </c:forEach>
      </select>
    </div>
    <div class="am-u-sm-4" style="line-height: 28px;">*必选</div>
  </div>
</form>
<script>
  $("select").selected();
</script>