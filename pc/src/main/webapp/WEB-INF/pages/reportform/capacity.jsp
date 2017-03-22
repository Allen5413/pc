<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findCapacity/find.html" method="post">
  <input type="hidden" name="resourceId" value="${requestScope.resourceId}" />
  <c:set var="isShowFindBtn" value="${my:isPermission(requestScope.resourceId,'find',sessionScope.menuMap)}" />

  <label >工作组：</label>
  <select id="wgId" name="wgId" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
    <option value=""></option>
    <option value="null">全部</option>
    <c:forEach var="workGroup" items="${wgList}">
      <option value="${workGroup.id}" <c:if test="${param.wgId eq workGroup.id}">selected="selected" </c:if> >${workGroup.name}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
  <label >工作中心：</label>
  <select id="wcId" name="wcId" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
    <option value=""></option>
    <option value="null">全部</option>
    <c:forEach var="workCore" items="${wcList}">
      <option value="${workCore.id}" <c:if test="${param.wcId eq workCore.id}">selected="selected" </c:if> >${workCore.name}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
  <label >班组：</label>
  <select id="cgId" name="cgId" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
    <option value=""></option>
    <option value="null">全部</option>
    <c:forEach var="classGroup" items="${cgList}">
      <option value="${classGroup.id}" <c:if test="${param.cgId eq classGroup.id}">selected="selected" </c:if> >${classGroup.name}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${isShowFindBtn}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
            onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
  </c:if>
</form>
<p /><p />
<c:forEach var="data" items="${resultList}">
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:100%; margin-left: 5px; margin-top: 10px;">
    <div class="am-panel-hd am-cf">${data.wgName}</div>
    <div class="am-in">
      <table id="findProductCgTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
        <tr class="am-primary" style="border-right: 0px;">
          <th style="width:10%;">工作中心</th>
          <th style="width:10%;">班组</th>
          <th style="width:10%;">班次</th>
          <th style="width:20%;">工作时间</th>
          <th style="width:10%;">是否加班</th>
          <th style="width:10%;">加班时间(小时)</th>
          <th>产能</th>
        </tr>
        <c:forEach var="wc" items="${data.wcList}">
          <tr>
            <td rowspan="${fn:length(wc.cgList)}">${wc.wcName}</td>
            <c:set var="isTr" value="false" />
            <c:forEach var="cg" items="${wc.cgList}">
              <c:if test="${isTr}"><tr></c:if>
                <td>${cg.cgName}</td>
                <td>${cg.wtName}</td>
                <td>${cg.workDate}</td>
                <td>
                  <c:if test="${'0' eq cg.isAddWork}">否</c:if>
                  <c:if test="${'0' ne cg.isAddWork}">是</c:if>
                </td>
                <td>${cg.addWorkTime}</td>
                <td>${cg.capacity}</td>
              <c:if test="${isTr}"></tr></c:if>
              <c:set var="isTr" value="true" />
            </c:forEach>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>
</c:forEach>

<script>
  $("select").selected();
</script>
