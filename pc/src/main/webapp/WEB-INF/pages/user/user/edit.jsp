<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="editForm" name="editForm" method="post">
  <input type="hidden" name="id" value="${userInfo.id}"/>
  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >登录名：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" readonly id="edit_loginName" name="loginName"
      value="${userInfo.loginName}"/>
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >姓名：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入姓名" required id="edit_name" name="name"
      value="${userInfo.name}"/>
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >电话：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入电话" required id="edit_phone" name="phone"
      value="${userInfo.phone}"/>
    </div>
    <div class="am-u-sm-5"></div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >状态：</label></div>
    <div class="am-u-sm-4">
      <div class="am-btn-group doc-js-btn-1" data-am-button>
        <label class="am-btn am-btn-primary am-btn-s <c:if test="${userInfo.state==1}"> am-active </c:if> ">
          <input type="radio" name="state" value="1" <c:if test="${userInfo.state==1}"> checked </c:if>> 启用
        </label>
        <label class="am-btn am-btn-primary am-btn-s <c:if test="${userInfo.state==2}"> am-active </c:if>">
          <input type="radio" name="state" value="2" <c:if test="${userInfo.state==2}"> checked </c:if>> 停用
        </label>
      </div>
    </div>
    <div class="am-u-sm-5"></div>
  </div>

  <div class="am-g am-margin-top-sm">
    <div class="am-u-sm-3 am-text-right"><label >备注：</label></div>
    <div class="am-u-sm-9">
      <textarea rows="6" placeholder="输入备注" name="remark">${userInfo.remark}</textarea>
    </div>
  </div>
</form>
