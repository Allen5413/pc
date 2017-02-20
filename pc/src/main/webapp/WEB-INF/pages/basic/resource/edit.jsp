<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editResourceForm" name="editResourceForm" method="post">
  <input type="hidden" name="id" value="${resource.id}" />
  <input type="hidden" name="version" value="${resource.version}" />
  <input type="hidden" name="menuId" value="${resource.menuId}" />
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="edit_name"><i class="red">*</i>资源名称</label>
    <div class="am-u-sm-10">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="edit_name" value="${resource.name}" name="name"  />
    </div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="edit_url"><i class="red">*</i>URL</label>
    <div class="am-u-sm-10">
      <input class="am-input-sm" type="text" placeholder="输入URL" required id="edit_url" name="url" value="${resource.url}" />
    </div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right">按钮信息</label>
    <div class="am-u-sm-10">
      <input type="hidden" id="buttons" name="buttons"  />
      <table class="am-table am-table-bordered am-table-striped no-margin-bottom" id="buttonArea" style="width:100%;">
        <tr class="am-primary">
          <th style="width: 40%; text-align: center;">名称</th>
          <th style="width: 40%; text-align: center;">编码</th>
          <th style="text-align: center;">操作</th>
        </tr>
        <c:if test="${empty buttons}">
          <tr>
            <td style="width: 40%;"><input class="am-input-sm" type="text" value="${buttonInfo.name}" name="button_name_${status.index}"/>
              <input type="hidden" value="0" name="button_id_${status.index}"/></td>
            <td style="width: 40%;"><input class="am-input-sm" type="text"  value="${buttonInfo.buttonCode}" name="button_code_${status.index}"/></td>
            <td><a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="addButton()"><span class="am-icon-plus"></span>添加</a></td>
          </tr>
        </c:if>
        <c:forEach var="buttonInfo" items="${buttons}" varStatus="status">
          <tr>
            <td style="width: 40%;"><input class="am-input-sm" type="text" value="${buttonInfo.name}" name="button_name_${status.index}"/>
              <input type="hidden" value="${buttonInfo.id}" name="button_id_${status.index}"/></td>
            <td style="width: 40%;"><input class="am-input-sm" type="text"  value="${buttonInfo.buttonCode}" name="button_code_${status.index}"/></td>
            <c:if test="${status.index==0}">
              <td><a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="addButton()"><span class="am-icon-plus"></span>&nbsp;添加</a></td>
            </c:if>
            <c:if test="${status.index!=0}">
              <td><a class="am-badge am-badge-danger am-radius am-text-lg" onClick="removeButton(this)"><span class="am-icon-trash-o"></span>&nbsp;删除</a></td>
            </c:if>
          </tr>
        </c:forEach>
      </table>
    </div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="remark">备注</label>
    <div class="am-u-sm-10">
      <textarea rows="3" class="am-input-sm" placeholder="输入备注" name="remark" id="remark">${resource.remark}</textarea>
    </div>
  </div>
</form>
<script>
  //添加按钮显示
          var buttonIndex = 100;
          function addButton(){
              $('#buttonArea tbody').append(' <tr>'+
                      '<td style="width: 40%;"><input class="am-input-sm" type="text" name="button_name_'+buttonIndex+'"/>' +
                      '<input type="hidden" value="0" name="button_id_'+buttonIndex+'"/></td>'+
                      '<td style="width: 40%;"><input class="am-input-sm" type="text" name="button_code_'+buttonIndex+'"/></td>'+
                      '<td><a class="am-badge am-badge-danger am-radius am-text-lg" onclick="removeButton(this)" >' +
                      '   <span class="am-icon-trash-o"></span>&nbsp;删除</a>' +
                      '</td>'+
                      '</tr>');
              buttonIndex++;
          }
          //删除目标行
          function removeButton(target){
              $(target).parent().parent().remove();
          }
</script>