<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findUserPage/find.htm" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>

  <label >用户名称：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >用户状态：</label>
  <select id="state" name="state" onchange="app.changeSelect(this)">
    <option value=""></option>
    <option value="null">全部</option>
    <option value="1" <c:if test="${param.state eq '1'}">selected="selected" </c:if> >启用</option>
    <option value="2" <c:if test="${param.state eq '2'}">selected="selected" </c:if> >停用</option>
  </select>

  <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
          data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
          onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
</form>
<p /><p />

<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <tr>
    <td colspan="999" style="background-color:#FFF">
      <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="add()"><span class="am-icon-plus"></span> 新增用户</button>
    </td>
  </tr>
  <tr class="am-primary">
    <th style="width: 5%;">序号</th>
    <th style="width: 8%;">登录名</th>
    <th style="width: 8%;">姓名</th>
    <th style="width: 8%;">电话</th>
    <th style="width: 8%;">状态</th>
    <th style="width: 20%;">备注</th>
    <th style="width: 7%;">操作人</th>
    <th style="width: 12%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="6" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="user" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${user.loginName}</td>
      <td>${user.name}</td>
      <td>${user.phone}</td>
      <td>${user.stateStr}</td>
      <td>${user.remark}</td>
      <td>${user.operator}</td>
      <td><fmt:formatDate value="${user.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <a class="am-badge am-badge-success am-radius am-text-lg" onClick="openUserGroup(${user.id})"><span class="am-icon-cog"></span> 关联用户组</a>
        <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${user.id})"><span class="am-icon-edit"></span> 修改</a>
        <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${user.id}, this)"><span class="am-icon-trash-o"></span> 删除</a>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>
  function edit(id){
    var url = '${pageContext.request.contextPath}/editUser/open.html?id='+id;
    app.openDialog(url, '编辑用户', 600, 0.4, function(index){
      var name = $("#edit_name").val().trim();
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      app.edit("${pageContext.request.contextPath}/editUser/editor.json", $('#editForm').serialize(), index);
    });
  }

  function add(){
    app.openDialog("${pageContext.request.contextPath}/addUser/open.html", "新增用户", 600, 450, function(index){
      app.add("${pageContext.request.contextPath}/addUser/add.json", $('#addForm').serialize(), index);
    });
  }

  function del(id, btnObj){
    app.del("您确定要删除该用户信息？", "${pageContext.request.contextPath}/delUser/del.json", {"id":id}, btnObj);
  }

  function openUserGroup(id){
    app.openDialog('${pageContext.request.contextPath}/addUserGroupUser/open.html?userId='+id, '关联用户组',
            500, 400,function(index){
        var selectUserGroup = [];
        $("input[name='userGroupCheck']:checked").each(function(){
            selectUserGroup.push($(this).val());
        });
        app.add("${pageContext.request.contextPath}/addUserGroupUser/add.json",
                {'userId':id,'userGroupIds':selectUserGroup.join(",")}, index,function(){});
      });
  }
</script>
