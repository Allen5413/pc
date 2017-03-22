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
  <input type="text" id="q_start" name="start" value="${param.start}" style="height: 30px;"
         onfocus="WdatePicker({firstDayOfWeek:1})" class="Wdate" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >结束日期：</label>
  <input type="text" id="q_end" name="end" value="${param.end}" style="height: 30px;"
         onfocus="WdatePicker({firstDayOfWeek:1})" class="Wdate"/>&nbsp;&nbsp;&nbsp;&nbsp;
  <label >产品名称：</label>
  <input type="text" id="q_name" name="name" value="${param.name}" style="height: 30px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
  <label >产品类型：</label>
  <select id="q_type" name="type"  onchange="app.changeSelect(this)"
          data-am-selected="{maxHeight: 300,btnWidth:'120px'}">
    <option value=""></option>
    <option value="null">全部</option>
    <c:forEach items="${productTypes}" var="productType">
      <option value="${productType.FCATEGORYID}" <c:if test="${param.type==productType.FCATEGORYID}"> selected </c:if>>${productType.FNAME}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
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
<div style="overflow: auto; width: 100%;">
  <table class="am-table am-table-bordered am-table-striped am-table-hover">
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
        <td>${planInfo.grossNum}</td>
        <td>${planInfo.planTotalNum}</td>
        <td>${planInfo.stockNum}</td>
        <c:forEach items="${planCycle}" var="plans">
          <td>${planInfo.plans[plans]['demandNum']}</td>
          <td>${planInfo.plans[plans]['productionNum']}</td>
          <td>${planInfo.plans[plans]['planNum']}</td>
        </c:forEach>
      </tr>
    </c:forEach>
  </table>
</div>
<%@ include file="../../common/page.jsp"%>
<script>
  $(function(){
     $('#calBtn').on('click',function(){
             app.openDialog("${pageContext.request.contextPath}/calProPlan/open.html", '计算生产', 400, 250, function(index){
                     var start = $("#start").val().trim();
                     var end = $("#end").val().trim();
                     if(start == ""){
                          app.msg("请输入开始时间", 1);
                          return;
                     }
                     if(start == ""){
                           app.msg("请输入结束时间", 1);
                           return;
                     }
                     $.ajax({
                             type: "POST",
                             url:"${pageContext.request.contextPath}/calProPlan/cal.json",
                             data:{start:start,end:end},
                             async: false,
                             success: function(data) {
                                     if(data.state == 0){
                                             app.msg('计算处理中', 0);
                                     }else{
                                             app.msg("正在计算中，请稍后", 1);
                                     }
                                     layer.close(index);
                             }
                     });
             });
     });
  });
</script>
