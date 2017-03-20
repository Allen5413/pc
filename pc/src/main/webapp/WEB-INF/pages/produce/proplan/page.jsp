<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findProPlan/find.html" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
  <input type="hidden" name="resourceId" value="${requestScope.resourceId}" />
  <c:set var="isShowFindBtn" value="${my:isPermission(requestScope.resourceId,'query',sessionScope.menuMap)}" />
  <label >开始日期：</label>
  <input type="text" id="start" name="start" value="${param.start}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >结束日期：</label>
  <input type="text" id="end" name="end" value="${param.end}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >产品名称：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >产品类型：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${isShowFindBtn}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
            onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
    </c:if>
  <c:if test="${my:isPermission(requestScope.resourceId,'cal',sessionScope.menuMap)}">
    <button type="button" id="calBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '计算中...', resetText: '计算错误'}"
            onclick=""><span class="am-icon-search"></span> 计算</button>
  </c:if>
</form>
<p /><p />

<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <c:if test="${isShowAddBtn}">
    <tr>
      <td colspan="999" style="background-color:#FFF">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="add()"><span class="am-icon-plus"></span> 新增</button>
      </td>
    </tr>
  </c:if>
  <tr class="am-primary">
    <th style="width:100px;text-align: center;vertical-align: middle;" rowspan="2">产品编码</th>
    <th style="width:100px;text-align: center;vertical-align: middle;" rowspan="2">产品名称</th>
    <th style="width:100px;text-align: center;vertical-align: middle;" rowspan="2">客户需求</th>
    <th style="width:100px;text-align: center;vertical-align: middle;" rowspan="2">净需求</th>
    <th style="width:100px;text-align: center;vertical-align: middle;" rowspan="2">库存</th>
    <c:forEach items="${planCycle}" var="plans">
      <th colspan="3" style="text-align: center;">${plans}</th>
    </c:forEach>
  </tr>
  <tr class="am-primary">
    <c:forEach items="${planCycle}" var="plans">
      <th>需求</th>
      <th>产能</th>
      <th>计划</th>
    </c:forEach>
  </tr>
  <c:if test="${empty proPlanInfo}">
    <tr>
      <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="planInfo" items="${proPlanInfo}" varStatus="status">
    <tr>
      <td>${planInfo.productNo}</td>
      <td>${planInfo.productName}</td>
      <td></td>
      <td></td>
      <td>${planInfo.stockNum}</td>
      <c:forEach items="${planCycle}" var="plans">
        <td>${planInfo.plans[plans]['demandNum']}</td>
        <td>${planInfo.plans[plans]['productionNum']}</td>
        <td>${planInfo.plans[plans]['planNum']}</td>
      </c:forEach>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>
  $(function(){
     $('#calBtn').on('click',function(){
             $.ajax({
                     type: "POST",
                     url:"${pageContext.request.contextPath}/calProPlan/cal.json",
                     async: false,
                     success: function(data) {
                             if(data.state == 0){
                                     app.msg('计算处理中', 0);
                             }else{
                                 app.msg("正在计算中，请稍后", 1);
                             }
                     }
             });
     });
  });
</script>
