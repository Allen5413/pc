<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<div id="userGourpResourceManage">
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:30%;float: left;">
    <div class="am-panel-hd am-cf">角色信息</div>
    <div id="with" class="am-in" style="overflow-y:auto;">
      <table id="userGroupTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
        <c:if test="${my:isPermission(requestScope.resourceId,'addRole',sessionScope.menuMap)}">
          <tr>
            <td colspan="2" style="background-color:#FFF">
              <button class="am-btn am-btn-primary am-btn-sm" id="addUserGroup" type="button"><span class="am-icon-plus"></span>添加</button>
            </td>
          </tr>
        </c:if>
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
    <div id="notWith" class="am-in">
        <div style="background-color: #fFF;width: 100%;padding: 0.7rem;border-bottom: solid 1px #ddd;">
          <button class="am-btn am-btn-primary am-btn-sm btn-loading-example" id="saveUserGroupResource" type="button"
                  data-am-loading="{spinner: 'circle-o-notch', loadingText: '保存中...'}">
            <span class="am-icon-save"></span>保存</button>
        </div>
        <ul id="resourceTree" class="ztree"></ul>
    </div>
  </div>
 </div>
<script>
 $(function(){
    $('#userGourpResourceManage .am-in').height( $('.am-tabs-bd').height()-65);
    var userGroupResourceManager={
        userGroupId:-1,
        userGroupResourceTree:null,
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
            this.userGroupId = userGroupId;
            userGroupResourceManager.userGroupResourceTree.checkAllNodes(false);
            app.getAjaxData('${pageContext.request.contextPath}/findResourceByGroupId/find.json',
                    {'userGroupId':this.userGroupId},false,this.reCheckedTree)
        },
        loadMenuTree:function(){
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
            this.userGroupResourceTree = $.fn.zTree.init($("#resourceTree"), setting, ${resourceTree});
        },
        addUserGroupResource:function(){
            if(this.userGroupId==-1){
                app.msg("请选择角色信息", 1);
                return false;
            }
            var selectIds = this.userGroupResourceTree.getCheckedNodes(true);
            var idsArr = [];
            $.each(selectIds,function(i,val){
                if(val.id>0){
                    idsArr.push(val.id);
                }
            });
            app.addAjax('${pageContext.request.contextPath}/addUserGroupResource/add.json',
                    {'userGroupId':this.userGroupId,'sourceIds':idsArr.join(',')},'saveUserGroupResource');
        },
        reCheckedTree:function(data){
            if(data){
                var treeNode = null;
               $.each(data,function(i,val){
                       console.log(val);
                   treeNode= userGroupResourceManager.userGroupResourceTree.getNodeByParam('id',val.resourceId,null);
                   userGroupResourceManager.userGroupResourceTree.checkNode(treeNode,true,true);
               });
            }
        }
    };
    $('#addUserGroup').on('click',function(){
        userGroupResourceManager.addUserGroup();
    });
     $('#saveUserGroupResource').on('click',function(){
           userGroupResourceManager.addUserGroupResource();
     });
   $('#userGroupTable').on('dblclick','tr',function(){
        var userGroupId = $(this).attr('data-id');
        if(userGroupId){
            if(userGroupResourceManager.userGroupId!=userGroupId){
                userGroupResourceManager.changeUserGroup(userGroupId);
            }
            userGroupResourceManager.userGroupId = userGroupId;
        }
    });
    userGroupResourceManager.loadMenuTree();
 });
</script>