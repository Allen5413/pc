<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="addForm" name="addForm" method="post">
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right"  for="add_name">菜单名称</label>
    <div class="am-u-sm-6">
      <input class="am-input-sm" type="text" placeholder="输入菜单名称" required id="add_name" name="name"  />
    </div>
    <div class="am-u-sm-4" style="line-height: 28px;">*必填，不可重复</div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="add_name">备注</label>
    <div class="am-u-sm-10">
      <textarea rows="4" placeholder="输入备注" name="remark"></textarea>
    </div>
  </div>
</form>