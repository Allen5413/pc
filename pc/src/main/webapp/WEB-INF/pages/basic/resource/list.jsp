<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <tr>
    <td colspan="999" style="background-color:#FFF">
      <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="addResource()"><span class="am-icon-plus"></span> 新增</button>
    </td>
  </tr>
  <tr class="am-primary">
    <th style="width: 6%;">序号</th>
    <th style="width: 10%;">名称</th>
    <th style="width: 12%;">URL</th>
    <th style="width: 18%;">备注</th>
    <th style="width: 6%;">操作人</th>
    <th style="width: 12%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty resourceList}">
    <tr>
      <td colspan="20" align="center" style="color: #ff000c;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="resource" items="${resourceList}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${resource.name}</td>
      <td>${resource.url}</td>
      <td>${resource.remark}</td>
      <td>${resource.operator}</td>
      <td><fmt:formatDate value="${resource.operateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="edit(${resource.id})"><span class="am-icon-edit"></span> 修改</a>
        <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${resource.id})"><span class="am-icon-trash-o"></span> 删除</a>
      </td>
    </tr>
  </c:forEach>
</table>
<script>
  function addResource(){
    app.openDialog('${pageContext.request.contextPath}/addResource/open.html?menuId=${param.menuId}', '新增资源', 700, 0.5, function(index){
      var name = $("#add_name").val().trim();
      var url = $("#add_url").val().trim();
      if(name == ""){
        app.msg("请输入名称", 1);
        return false;
      }
      if(url == ""){
        app.msg("请输入url", 1);
        return false;
      }
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/addResource/add.json",
        data:$('#addResourceForm').serialize(),
        async: false,
        success: function(data) {
          if(data.state == 0){
            app.msg("提交成功！", 0);
            layer.closeAll();
            app.openOneBtnDialog('${pageContext.request.contextPath}/findResourceByMenuId/open.html?menuId=${param.menuId}', '关联资源', 1200, 0.8);
          }else{
            app.msg(data.msg, 1);
          }
        }
      });
    });
  }

  function edit(id){
    app.openDialog('${pageContext.request.contextPath}/editResource/open.html?id='+id, '编辑资源', 700, 0.5, function(index){
      var name = $("#edit_name").val().trim();
      var url = $("#edit_url").val().trim();
      if(name == ""){
        app.msg("请输入名称", 1);
        return false;
      }
      if(url == ""){
        app.msg("请输入url", 1);
        return false;
      }
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/editResource/editor.json",
        data:$('#editResourceForm').serialize(),
        async: false,
        success: function(data) {
          if(data.state == 0){
            app.msg("提交成功！", 0);
            layer.closeAll();
            app.openOneBtnDialog('${pageContext.request.contextPath}/findResourceByMenuId/open.html?menuId=${param.menuId}', '关联资源', 1200, 0.8);
          }else{
            app.msg(data.msg, 1);
          }
        }
      });
    });
  }

  function del(id){
    app.confirm("您确定要删除该资源信息？", function(){
      $.ajax({
        url:"${pageContext.request.contextPath}/delReource/del.json",
        method : 'POST',
        async:false,
        data:{"id":id},
        success:function(data){
          if(data.state == 0){
            app.msg("删除成功！", 0);
            layer.closeAll();
            app.openOneBtnDialog('${pageContext.request.contextPath}/findResourceByMenuId/open.html?menuId=${param.menuId}', '关联资源', 1200, 0.8);
          }else {
            app.msg(data.msg, 1);
          }
        }
      });
    });
  }
</script>