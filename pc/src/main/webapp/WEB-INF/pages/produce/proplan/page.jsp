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
  <input type="text" id="code" name="code" value="${param.code}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >结束日期：</label>
  <input type="text" id="code" name="code" value="${param.code}" />&nbsp;&nbsp;&nbsp;&nbsp;
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
    <th colspan="3" style="text-align: center;">2017-03-01</th>
    <th colspan="3" style="text-align: center;">2017-03-02</th>
    <th colspan="3" style="text-align: center;">2017-03-03</th>
    <th colspan="3" style="text-align: center;">2017-03-04</th>
    <th colspan="3" style="text-align: center;">2017-03-05</th>
  </tr>
  <tr class="am-primary">
    <th>需求</th>
    <th>产能</th>
    <th>计划</th>
    <th>需求</th>
    <th>产能</th>
    <th>计划</th>
    <th >需求</th>
    <th>产能</th>
    <th>计划</th>
    <th>需求</th>
    <th>产能</th>
    <th>计划</th>
    <th >需求</th>
    <th>产能</th>
    <th >计划</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="classGroup" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${classGroup.code}</td>
      <td>${classGroup.name}</td>
      <td>${classGroup.operator}</td>
      <td><fmt:formatDate value="${classGroup.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>

  function edit(id){
    var url = '${pageContext.request.contextPath}/editClassGroup/open.html?id='+id;
    app.openDialog(url, '编辑班组', 600, 260, function(index){
      var code = $("#edit_code").val().trim();
      var name = $("#edit_name").val().trim();
      if(code == ""){
        app.msg("请输入编号", 1);
        return;
      }
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      app.edit("${pageContext.request.contextPath}/editClassGroup/editor.json", $('#editForm').serialize(), index);
    });
  }
</script>
