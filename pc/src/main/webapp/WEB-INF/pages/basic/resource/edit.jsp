<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="editResourceForm" method="post">
  <input type="hidden" name="id" value="${resource.id}" />
  <input type="hidden" name="version" value="${resource.version}" />

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >资源名称：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入资源名称" required id="edit_name" name="name" value="${resource.name}" />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >URL：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入URL" required id="edit_url" name="url" value="${resource.url}"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top-sm">
    <div class="am-u-sm-3 am-text-right"><label >备注：</label></div>
    <div class="am-u-sm-9">
      <textarea rows="6" placeholder="输入备注" name="remark">${resource.remark}</textarea>
    </div>
  </div>
</form>