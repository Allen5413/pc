<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="userGourpResourceManage">
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:30%;float: left;">
    <div class="am-panel-hd am-cf">角色信息</div>
    <div id="with" class="am-in" style="overflow-y:auto;">
      <table id="userGroupTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
        <tr>
          <td colspan="2" style="background-color:#FFF">
            <button class="am-btn am-btn-primary am-btn-sm" id="addUserGroup" type="button"><span class="am-icon-plus"></span>添加</button>
          </td>
        </tr>
        <tr class="am-primary">
          <th style="width: 20%;">序号</th>
          <th style="width: 80%;">名称</th>
        </tr>
        <c:forEach items="${userGroupList}" var="userGroup" varStatus="status">
          <tr  data-id="${userGroup.id}">
            <td>${status.index+1}</td>
            <td>${userGroup.name}</td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:69%; float: left;margin-left: 10px;">
    <div class="am-panel-hd am-cf">菜单信息</div>
    <div id="notWith" class="am-in" style="overflow-y:auto;">
        <ul id="resourceTree" class="ztree"></ul>
    </div>
  </div>
 </div>
<script>
 $(function(){
    $('#userGourpResourceManage .am-in').height( $('.am-tabs-bd').height()-65);
    var userGroupResourceManager={
        addUserGroup:function(){
            app.openDialog("${pageContext.request.contextPath}/addUserGroup/open.html", "新增角色", 400, 300, function(index){
                var name = $("#add_name").val().trim();
                if(name == ""){
                    app.msg("请输入角色名称", 1);
                    return false;
                  }
                app.add("${pageContext.request.contextPath}/addUserGroup/add.json",
                        $('#addUserGroupFrom').serialize(), index,userGroupResourceManager.addUserGroupRow);
            });
        },
        addUserGroupRow:function(userGroupData){
            var rowVal = '<tr data-id="'+userGroupData.id+'">'+
                           '<td>'+ ($('#userGroupTable tbody tr').length-1)+'</td>'+
                           '<td>'+userGroupData.name+'</td>' +
                        '</tr>';
            $('#userGroupTable tbody').append(rowVal);
        },
        changeUserGroup:function(userGroupId){

        }
    };
    $('#addUserGroup').on('click',function(){
        userGroupResourceManager.addUserGroup();
    });
   $('#userGroupTable').on('click','tr',function(){
        var userGroupId = $(this).attr('data-id');
        if(userGroupId){
            userGroupResourceManager.changeUserGroup(userGroupId);
        }
    });
   //初始化资源树
     var setting = {
             check: {
                 enable: true
             },
             data: {
                 simpleData: {
                     enable: true
                 }
             }
     };

     var zNodes =[
                 { id:1, pId:0, name:"can check 1", open:true},
                 { id:11, pId:1, name:"can check 1-1", open:true},
                 { id:111, pId:11, name:"can check 1-1-1"},
                 { id:112, pId:11, name:"can check 1-1-2"},
                 { id:12, pId:1, name:"can check 1-2", open:true},
                 { id:121, pId:12, name:"can check 1-2-1"},
                 { id:122, pId:12, name:"can check 1-2-2"},
                 { id:2, pId:0, name:"can check 2", checked:true, open:true},
                 { id:21, pId:2, name:"can check 2-1"},
                 { id:22, pId:2, name:"can check 2-2", open:true},
                 { id:221, pId:22, name:"can check 2-2-1", checked:true},
                 { id:222, pId:22, name:"can check 2-2-2"},
                 { id:23, pId:2, name:"can check 2-3"}
                 ];
   $.fn.zTree.init($("#resourceTree"), setting, zNodes);
 });
</script>