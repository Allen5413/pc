<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findCustomerPage/find.html" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>

  <label >编码：</label>
  <input type="text" id="code" name="code" value="${param.code}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >名称：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${my:isPermission(requestScope.resourceId,'find',sessionScope.menuMap)}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
            onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
  </c:if>
</form>
<p /><p />

<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <tr>
    <td colspan="999" style="background-color:#FFF">
      <c:if test="${my:isPermission(requestScope.resourceId,'add',sessionScope.menuMap)}">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="add()"><span class="am-icon-plus"></span> 新增</button>
      </c:if>
    </td>
  </tr>
  <tr class="am-primary">
    <th style="width: 5%;">序号</th>
    <th style="width: 15%;">编号</th>
    <th style="width: 20%;">名称</th>
    <th style="width: 10%;">操作人</th>
    <th style="width: 15%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="6" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="customer" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${customer.code}</td>
      <td>${customer.name}</td>
      <td>${customer.operator}</td>
      <td><fmt:formatDate value="${customer.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <c:if test="${my:isPermission(requestScope.resourceId,'edit',sessionScope.menuMap)}">
          <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${customer.id})"><span class="am-icon-edit"></span> 修改</a>
        </c:if>
        <c:if test="${my:isPermission(requestScope.resourceId,'del',sessionScope.menuMap)}">
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${customer.id})"><span class="am-icon-trash-o"></span> 删除</a>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>

  function edit(id){
    var url = '${pageContext.request.contextPath}/editCustomer/open.html?id='+id;
    app.openDialog(url, '编辑客户信息', 600, 260, function(index){
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
      app.edit("${pageContext.request.contextPath}/editCustomer/editor.json", $('#editForm').serialize(), index);
    });
  }

  function add(){
    app.openDialog("${pageContext.request.contextPath}/addCustomer/open.html", "新增客户信息", 600, 260, function(index){
      var code = $("#add_code").val().trim();
      var name = $("#add_name").val().trim();
      if(code == ""){
        app.msg("请输入编号", 1);
        return;
      }
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      app.add("${pageContext.request.contextPath}/addCustomer/add.json", $('#addForm').serialize(), index);
    });
  }

  function del(id){
    app.del("您确定要删除该客户信息？", "${pageContext.request.contextPath}/delCustomer/del.json", {"id":id});
  }
</script>
