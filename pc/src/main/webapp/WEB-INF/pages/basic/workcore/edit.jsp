<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editForm" method="post">
  <input type="hidden" name="id" value="${workCore.id}" />

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >编号：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入编号" required id="edit_code" name="code" value="${workCore.code}"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >名称：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="edit_name" name="name" value="${workCore.name}"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >是否公用：</label></div>
    <div class="am-u-sm-4">
      <div class="am-btn-group doc-js-btn-1" data-am-button>
        <label class="am-btn am-btn-primary am-btn-s <c:if test='${workCore.isPublic == 1}'>am-active</c:if>">
          <input type="radio" name="isPublic" value="1" <c:if test="${workCore.isPublic == 1}">checked</c:if> > 是
        </label>
        <label class="am-btn am-btn-primary am-btn-s <c:if test='${workCore.isPublic == 0}'>am-active</c:if>">
          <input type="radio" name="isPublic" value="0" <c:if test="${workCore.isPublic == 0}">checked</c:if> > 否
        </label>
      </div>
    </div>
    <div class="am-u-sm-5"></div>
  </div>
</form>