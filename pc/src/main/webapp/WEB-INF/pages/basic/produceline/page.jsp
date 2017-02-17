<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findProduceLinePage/find.html" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>

  <label >编码：</label>
  <input type="text" id="code" name="code" value="${param.code}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >名称：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
          data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
          onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
</form>
<p /><p />

<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <tr>
    <td colspan="999" style="background-color:#FFF">
      <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="add()"><span class="am-icon-plus"></span> 新增</button>
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
  <c:forEach var="produceLine" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${produceLine.code}</td>
      <td>${produceLine.name}</td>
      <td>${produceLine.operator}</td>
      <td><fmt:formatDate value="${produceLine.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <a class="am-badge am-badge-success am-radius am-text-lg" onClick="setProduceLineCore(${produceLine.id})"><span class="am-icon-cog"></span> 关联工作中心</a>
        <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${produceLine.id})"><span class="am-icon-edit"></span> 修改</a>
        <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${produceLine.id})"><span class="am-icon-trash-o"></span> 删除</a>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>

  function edit(id){
    var url = '${pageContext.request.contextPath}/editProduceLine/open.html?id='+id;
    app.openDialog(url, '编辑生产线', 600, 260, function(index){
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
      app.edit("${pageContext.request.contextPath}/editProduceLine/editor.json", $('#editForm').serialize(), index);
    });
  }

  function add(){
    app.openDialog("${pageContext.request.contextPath}/addProduceLine/open.html", "新增生产线", 600, 260, function(index){
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
      app.add("${pageContext.request.contextPath}/addProduceLine/add.json", $('#addForm').serialize(), index);
    });
  }

  function del(id){
    app.del("您确定要删除该生产线？", "${pageContext.request.contextPath}/delProduceLine/del.json", {"id":id});
  }

  function setProduceLineCore(id){
    app.openDialog('${pageContext.request.contextPath}/setProduceLineCoreForPlId/open.html?plId='+id, '关联工作中心', 800, 600, function(index){
      app.add("${pageContext.request.contextPath}/setProduceLineCoreForPlId/set.json", $('#setForm').serialize(), index);
    });
  }
</script>
