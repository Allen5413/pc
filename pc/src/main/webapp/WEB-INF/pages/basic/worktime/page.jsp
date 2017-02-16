<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findWorkTimePage/find.html" method="post">
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
    <th style="width: 10%;">编号</th>
    <th style="width: 10%;">名称</th>
    <th style="width: 15%;">开始时间</th>
    <th style="width: 15%;">结束时间</th>
    <th style="width: 10%;">操作人</th>
    <th style="width: 15%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="999" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="workTime" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${workTime.code}</td>
      <td>${workTime.name}</td>
      <td>${workTime.beginTimeStr}</td>
      <td>${workTime.endTimeStr}</td>
      <td>${workTime.operator}</td>
      <td><fmt:formatDate value="${workTime.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${workTime.id})"><span class="am-icon-edit"></span> 修改</a>
        <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${workTime.id})"><span class="am-icon-trash-o"></span> 删除</a>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>

  function edit(id){
    var url = '${pageContext.request.contextPath}/editWorkTime/open.html?id='+id;
    app.openDialog(url, '编辑班次信息', 600, 360, function(index){
      var code = $("#edit_code").val().trim();
      var name = $("#edit_name").val().trim();
      var beginTime = $("#edit_beginTime").val().trim();
      var endTime = $("#edit_endTime").val().trim();
      if(code == ""){
        app.msg("请输入编号", 1);
        return;
      }
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      if(beginTime == ""){
        app.msg("请选择开始时间", 1);
        return;
      }
      if(endTime == ""){
        app.msg("请选择结束时间", 1);
        return;
      }
      if(beginTime >= endTime){
        app.msg("开始时间不能大于等于结束时间", 1);
        return;
      }
      $("#edit_beginTime").val(beginTime+":00");
      $("#edit_endTime").val(endTime+":00");
      app.edit("${pageContext.request.contextPath}/editWorkTime/editor.json", $('#editForm').serialize(), index);
    });
  }

  function add(){
    app.openDialog("${pageContext.request.contextPath}/addWorkTime/open.html", "新增班次信息", 600, 360, function(index){
      var code = $("#add_code").val().trim();
      var name = $("#add_name").val().trim();
      var beginTime = $("#add_beginTime").val().trim();
      var endTime = $("#add_endTime").val().trim();
      if(code == ""){
        app.msg("请输入编号", 1);
        return;
      }
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      if(beginTime == ""){
        app.msg("请选择开始时间", 1);
        return;
      }
      if(endTime == ""){
        app.msg("请选择结束时间", 1);
        return;
      }
      if(beginTime >= endTime){
        app.msg("开始时间不能大于等于结束时间", 1);
        return;
      }
      $("#add_beginTime").val(beginTime+":00");
      $("#add_endTime").val(endTime+":00");
      app.add("${pageContext.request.contextPath}/addWorkTime/add.json", $('#addForm').serialize(), index);
    });
  }

  function del(id){
    app.del("您确定要删除该班次信息？", "${pageContext.request.contextPath}/delWorkTime/del.json", {"id":id});
  }
</script>
