<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editForm" method="post">
  <input type="hidden" name="id" value="${productScheduling.id}" />
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right"  for="count">生产数量</label>
    <div class="am-u-sm-6">
      <input class="am-input-sm" type="text" placeholder="输入生产数量" required id="count" value="${productScheduling.capacity}" name="count"  />
    </div>
    <div class="am-u-sm-4" style="line-height: 28px;">*必填</div>
  </div>
</form>