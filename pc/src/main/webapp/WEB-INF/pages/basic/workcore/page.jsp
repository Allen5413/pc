<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findWorkCorePage/find.html" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
  <input type="hidden" name="resourceId" value="${requestScope.resourceId}" />

  <c:set var="isShowAddBtn" value="${my:isPermission(requestScope.resourceId,'add',sessionScope.menuMap)}" />
  <c:set var="isShowEditBtn" value="${my:isPermission(requestScope.resourceId,'edit',sessionScope.menuMap)}" />
  <c:set var="isShowDelBtn" value="${my:isPermission(requestScope.resourceId,'del',sessionScope.menuMap)}" />
  <c:set var="isShowFindBtn" value="${my:isPermission(requestScope.resourceId,'find',sessionScope.menuMap)}" />
  <c:set var="isShowSetWorkGroupBtn" value="${my:isPermission(requestScope.resourceId,'setWorkGroup',sessionScope.menuMap)}" />
  <c:set var="isShowSetProduceLineBtn" value="${my:isPermission(requestScope.resourceId,'setProduceLine',sessionScope.menuMap)}" />

  <table width="97%">
    <tr height="40">
      <td align="right"><label >编码：</label></td>
      <td><input type="text" id="code" name="code" style="height: 28px;" value="${param.code}" /></td>
      <td align="right"><label >名称：</label></td>
      <td><input type="text" id="name" name="name" style="height: 28px;" value="${param.name}" /></td>
      <td align="right"><label >工作组：</label></td>
      <td>
        <select id="wgId" name="wgId" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
          <option value=""></option>
          <option value="null">全部</option>
          <c:forEach var="workGroup" items="${workGroupList}">
            <option value="${workGroup.id}" <c:if test="${param.wgId eq workGroup.id}">selected="selected" </c:if> >${workGroup.name}</option>
          </c:forEach>
        </select>
      </td>
      <td align="right"><label >生产线：</label></td>
      <td>
        <select id="plId" name="plId" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
          <option value=""></option>
          <option value="null">全部</option>
          <c:forEach var="produceLine" items="${produceLineList}">
            <option value="${produceLine.id}" <c:if test="${param.plId eq produceLine.id}">selected="selected" </c:if> >${produceLine.name}</option>
          </c:forEach>
        </select>
      </td>
      <c:if test="${isShowFindBtn}">
        <td style="width:100px;">
          <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
                  data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
                  onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
        </td>
      </c:if>
    </tr>

  </table>
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
    <th style="width: 4%;">序号</th>
    <th style="width: 8%;">编号</th>
    <th style="width: 14%;">名称</th>
    <th style="width: 8%;">操作人</th>
    <th style="width: 15%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="workCore" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${workCore.code}</td>
      <td>${workCore.name}</td>
      <td>${workCore.operator}</td>
      <td><fmt:formatDate value="${workCore.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <c:if test="${isShowSetWorkGroupBtn}">
          <a class="am-badge am-badge-success am-radius am-text-lg" onClick="setWorkGroupCore(${workCore.id})"><span class="am-icon-cog"></span> 关联工作组</a>
        </c:if>
        <c:if test="${isShowEditBtn}">
          <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${workCore.id})"><span class="am-icon-edit"></span> 修改</a>
        </c:if>
        <c:if test="${isShowDelBtn}">
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${workCore.id})"><span class="am-icon-trash-o"></span> 删除</a>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>
  function edit(id){
    var url = '${pageContext.request.contextPath}/editWorkCore/open.html?id='+id;
    app.openDialog(url, '编辑工作中心', 600, 260, function(index){
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
      app.edit("${pageContext.request.contextPath}/editWorkCore/editor.json", $('#editForm').serialize(), index);
    });
  }

  function add(){
    app.openDialog("${pageContext.request.contextPath}/addWorkCore/open.html", "新增工作中心", 600, 260, function(index){
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
      app.add("${pageContext.request.contextPath}/addWorkCore/add.json", $('#addForm').serialize(), index);
    });
  }

  function del(id){
    app.del("您确定要删除该工作中心信息？", "${pageContext.request.contextPath}/delWorkCore/del.json", {"id":id});
  }

  function setWorkGroupCore(id){
    app.openDialog('${pageContext.request.contextPath}/setWorkGroupCoreForWcId/open.html?wcId='+id, '关联工作组', 800, 600, function(index){
      app.add("${pageContext.request.contextPath}/setWorkGroupCoreForWcId/set.json", $('#setForm').serialize(), index);
    });
  }

  function setProduceLineCore(id){
    app.openDialog('${pageContext.request.contextPath}/setProduceLineCoreForWcId/open.html?wcId='+id, '关联生产线', 800, 600, function(index){
      app.add("${pageContext.request.contextPath}/setProduceLineCoreForWcId/set.json", $('#setForm').serialize(), index);
    });
  }
</script>
