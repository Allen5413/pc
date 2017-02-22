<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editForm" method="post">
  <input type="hidden" name="id" value="${produceLine.id}" />

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >关联生产中心：</label></div>
    <div class="am-u-sm-4" style="float: left">
      <c:if test="${empty wcList}">
        <span style="color: red">暂时还没有关联的工作中心</span>
      </c:if>
      <c:if test="${!empty wcList}">
        <c:forEach var="wc" items="${wcList}" varStatus="status">
          <c:if test="${0 < status.index}">
            &nbsp;<span class="am-icon-arrow-right"></span>&nbsp;
          </c:if>
          ${wc.name}
        </c:forEach>
      </c:if>
    </div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >编号：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入编号" required id="edit_code" name="code" value="${produceLine.code}"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >名称：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="edit_name" name="name" value="${produceLine.name}"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>
</form>