<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<style type="text/css">
  .table-head{padding-right:17px;}
  .table-body{width:100%;overflow-y:scroll;}
  .table-head table,.table-body table{width:100%;}
</style>
<div id="userGourpResourceManage">
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:30%;float: left;">
    <div class="am-panel-hd am-cf">角色信息</div>
    <div id="with" class="am-in">
      <c:if test="${my:isPermission(requestScope.resourceId,'addRole',sessionScope.menuMap)}">
        <div style="background-color: #fFF;width: 100%;padding: 0.7rem;">
          <button class="am-btn am-btn-primary am-btn-sm" id="addUserGroup" type="button"><span class="am-icon-plus"></span>&nbsp;&nbsp;添加</button>
        </div>
      </c:if>
      <div class="table-head">
          <table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
            <tr class="am-primary" style="border-right: 0px;">
              <th style="width: 20%;">序号</th>
              <th style="width: 80%;">名称</th>
            </tr>
          </table>
      </div>
      <div class="table-body">
        <table id="userGroupTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
          <c:forEach items="${userGroupList}" var="userGroup" varStatus="status">
            <tr  data-id="${userGroup.id}" >
              <td style="width: 20%;">${status.index+1}</td>
              <td style="width: 80%;">${userGroup.name}</td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </div>
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:68%; float: left;margin-left: 10px;">
    <div class="am-panel-hd am-cf">菜单信息</div>
    <div id="notWith" class="am-in">
        <c:if test="${my:isPermission(requestScope.resourceId,'saveAuth',sessionScope.menuMap)}">
          <div style="background-color: #fFF;width: 100%;padding: 0.7rem;border-bottom: solid 1px #ddd;">
            <button class="am-btn am-btn-primary am-btn-sm btn-loading-example" id="saveUserGroupResource" type="button"
                    data-am-loading="{spinner: 'circle-o-notch', loadingText: '保存中...'}">
              <span class="am-icon-save"></span>&nbsp;&nbsp;保存</button>
          </div>
        </c:if>
        <div id="zTreeDiv" style="overflow: auto;">
          <ul id="resourceTree" class="ztree"></ul>
        </div>
    </div>
  </div>
 </div>
<script>
 $(function(){
    $('#userGourpResourceManage .table-body').height( $('.am-tabs-bd').height()-147);
   $('#zTreeDiv').height( $('.am-tabs-bd').height()-112);
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
                           '<td>'+ ($('#userGroupTable tbody tr').length+1)+'</td>'+
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
                   treeNode= userGroupResourceManager.userGroupResourceTree.getNodeByParam('id',val.resourceId,null);
                   userGroupResourceManager.userGroupResourceTree.checkNode(treeNode,true);
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
     $('#userGroupTable').on('click','tr',function(){
        $('#userGroupTable tr').removeClass('am-active');
        $(this).addClass('am-active');
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