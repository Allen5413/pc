<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="editForm" method="post">
  <input type="hidden" name="id" value="${menu.id}" />
  <input type="hidden" name="version" value="${menu.version}" />

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >菜单名称：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入菜单名称" required id="edit_name" name="name" value="${menu.name}" />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top-sm">
    <div class="am-u-sm-3 am-text-right"><label >备注：</label></div>
    <div class="am-u-sm-9">
      <textarea rows="6" placeholder="输入备注" name="remark">${menu.remark}</textarea>
    </div>
  </div>
</form>