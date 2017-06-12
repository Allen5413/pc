<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<style>
    .am-primary > th:first-child{text-align:center;}
    .am-table > thead > tr > th:nth-of-tpye(1),
    .am-table > thead > tr > th:nth-of-tpye(2),
    .am-table > thead > tr > th:nth-of-tpye(3),
    .am-table > tbody > tr > td:nth-of-tpye(1),
    .am-table > tbody > tr > td:nth-of-tpye(2),
    .am-table > tbody > tr > td:nth-of-tpye(3){
        position:fixed; left:0;}
    .scroll-y{overflow-y:scroll;}

    .scroll-content{width:3000px;}
</style>
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
    <option value="">全部</option>
    <c:forEach items="${productTypes}" var="productType">
      <option value="${productType.FCATEGORYID}" <c:if test="${param.type==productType.FCATEGORYID}"> selected </c:if>>${productType.FNAME}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${isShowFindBtn}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
            onclick="app.searchFormPage($('#pageForm'), '${pageContext.request.contextPath}/findProPlan/find.html', this)"><span class="am-icon-search"></span> 查询</button>
    </c:if>
  <c:if test="${my:isPermission(requestScope.resourceId,'cal',sessionScope.menuMap)}">
    <button type="button" id="calBtn" class="am-btn am-btn-primary"><span class="am-icon-search"></span> 计算</button>
  </c:if>
  <button type="button" id="exportBtn" class="am-btn am-btn-primary"><span class="am-icon-file-excel-o"></span> 导出</button>
</form>
<p /><p />
<div style="overflow: auto; width: 100%;">
  <div class="scroll-content" style="width:3000px">
      <table class="am-table am-table-bordered am-table-striped am-table-hover" style="width: 3000px;">
        <thead>
        <c:if test="${isShowAddBtn}">
          <tr>
            <td colspan="999" style="background-color:#FFF">
              <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="add()"><span class="am-icon-plus"></span> 新增</button>
            </td>
          </tr>
        </c:if>
        <tr class="am-primary">
          <th style="width:150px;text-align: center;vertical-align: middle;" rowspan="2">产品编码</th>
          <th style="width:450px;text-align: center;vertical-align: middle;" rowspan="2">产品名称</th>
          <th style="width:100px;text-align: center;vertical-align: middle;display: none;" rowspan="2">客户需求</th>
          <th style="width:100px;text-align: center;vertical-align: middle;" rowspan="2">净需求</th>
          <th style="width:100px;text-align: center;vertical-align: middle;display: none;" rowspan="2" >库存</th>
          <c:forEach items="${planCycle}" var="plans">
            <th colspan="3" style="width:300px;text-align: center;">${plans}</th>
          </c:forEach>
        </tr>
        <tr class="am-primary">
          <c:forEach items="${planCycle}" var="plans">
            <th style="width:100px;text-align: center;">需求</th>
            <th style="width:100px;text-align: center;">产能</th>
            <th style="width:100px;text-align: center;">计划</th>
          </c:forEach>
        </tr>
        </thead>
        <TBODY style="width:3000px;">
        <c:if test="${empty proPlanInfo}">
          <tr>
            <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
          </tr>
        </c:if>
        <c:forEach var="planInfo" items="${proPlanInfo}" varStatus="status">
          <tr>
            <td>${planInfo.productNo}</td>
            <td>${planInfo.productName}</td>
            <td style="display: none;">${planInfo.grossNum}</td>
            <td>${planInfo.planTotalNum}</td>
            <td style="display: none;">${planInfo.stockNum}</td>
            <c:forEach items="${planCycle}" var="plans">
              <td>${planInfo.plans[plans]['planNum']}</td>
              <td>${planInfo.plans[plans]['capacity']}</td>
              <td>${planInfo.plans[plans]['actualProductionNum']}</td>
            </c:forEach>
          </tr>
        </c:forEach>
        </TBODY>
      </table>
  </div>
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
                     $('#q_start').val(start);
                     $('#q_end').val(end);
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
      $('#exportBtn').on('click',function(){
              $('#pageForm').attr('action','${pageContext.request.contextPath}/exportProPlan/export.html');
              $('#pageForm').submit();
      });
  });
</script>
