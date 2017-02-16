<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="addUserGroupFrom" name="addUserGroupFrom" method="post">
  <div class="am-form-group">
    <label class="am-u-sm-3 am-form-label no-padding-right " for="add_name">角色名称</label>
    <div class="am-u-sm-5  no-padding-right">
      <input class="am-input-sm" type="text" placeholder="输入角色名称" required id="add_name" name="name"  />
    </div>
    <div class="am-u-sm-4" style="line-height: 28px;">*必填，不可重复</div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-3 am-form-label no-padding-right">备注</label>
    <div class="am-u-sm-9">
      <textarea rows="6" placeholder="输入备注" name="remark"></textarea>
    </div>
  </div>
</form>