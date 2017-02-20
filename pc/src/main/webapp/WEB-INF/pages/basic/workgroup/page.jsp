<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findWorkGroupPage/find.html" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
  <input type="hidden" name="resourceId" value="${requestScope.resourceId}" />

  <c:set var="isShowAddBtn" value="${my:isPermission(requestScope.resourceId,'add',sessionScope.menuMap)}" />
  <c:set var="isShowEditBtn" value="${my:isPermission(requestScope.resourceId,'edit',sessionScope.menuMap)}" />
  <c:set var="isShowDelBtn" value="${my:isPermission(requestScope.resourceId,'del',sessionScope.menuMap)}" />
  <c:set var="isShowFindBtn" value="${my:isPermission(requestScope.resourceId,'find',sessionScope.menuMap)}" />
  <c:set var="isShowSetWorkCoreBtn" value="${my:isPermission(requestScope.resourceId,'setWorkCore',sessionScope.menuMap)}" />

  <label >编码：</label>
  <input type="text" id="code" name="code" value="${param.code}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >名称：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${isShowFindBtn}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
            onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
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
    <th style="width: 5%;">序号</th>
    <th style="width: 15%;">编号</th>
    <th style="width: 20%;">名称</th>
    <th style="width: 10%;">操作人</th>
    <th style="width: 15%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="workGroup" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${workGroup.code}</td>
      <td>${workGroup.name}</td>
      <td>${workGroup.operator}</td>
      <td><fmt:formatDate value="${workGroup.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <c:if test="${isShowSetWorkCoreBtn}">
          <a class="am-badge am-badge-success am-radius am-text-lg" onClick="setWorkGroupCore(${workGroup.id})"><span class="am-icon-cog"></span> 关联工作中心</a>
        </c:if>
        <c:if test="${isShowEditBtn}">
          <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${workGroup.id})"><span class="am-icon-edit"></span> 修改</a>
        </c:if>
        <c:if test="${isShowDelBtn}">
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${workGroup.id})"><span class="am-icon-trash-o"></span> 删除</a>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>

  function edit(id){
    var url = '${pageContext.request.contextPath}/editWorkGroup/open.html?id='+id;
    app.openDialog(url, '编辑工作组', 600, 260, function(index){
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
      app.edit("${pageContext.request.contextPath}/editWorkGroup/editor.json", $('#editForm').serialize(), index);
    });
  }

  function add(){
    app.openDialog("${pageContext.request.contextPath}/addWorkGroup/open.html", "新增工作组", 600, 260, function(index){
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
      app.add("${pageContext.request.contextPath}/addWorkGroup/add.json", $('#addForm').serialize(), index);
    });
  }

  function del(id){
    app.del("您确定要删除该工作组信息？", "${pageContext.request.contextPath}/delWorkGroup/del.json", {"id":id});
  }

  function setWorkGroupCore(id){
    app.openDialog('${pageContext.request.contextPath}/setWorkGroupCoreForWgId/open.html?wgId='+id, '关联工作中心', 800, 600, function(index){
      app.add("${pageContext.request.contextPath}/setWorkGroupCoreForWgId/set.json", $('#setForm').serialize(), index);
    });
  }
</script>
