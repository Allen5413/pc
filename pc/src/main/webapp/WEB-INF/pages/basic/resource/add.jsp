<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="addResourceForm" name="addResourceForm" method="post">
  <input type="hidden" name="menuId" value="${param.menuId}" />
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="add_name"><i class="red">*</i>资源名称</label>
    <div class="am-u-sm-10">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="add_name" name="name"  />
    </div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="add_name"><i class="red">*</i>URL</label>
    <div class="am-u-sm-10">
      <input class="am-input-sm" type="text" placeholder="输入URL" required id="add_url" name="url"  />
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
        <tr>
          <td style="width: 40%;"><input class="am-input-sm" type="text" name="button_name_0"/></td>
          <td style="width: 40%;"><input class="am-input-sm" type="text" name="button_code_0"/></td>
          <td><a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="addButton()"><span class="am-icon-plus"></span>添加</a></td>
        </tr>
      </table>
    </div>
  </div>
  <div class="am-form-group">
    <label class="am-u-sm-2 am-form-label no-padding-right" for="remark">备注</label>
    <div class="am-u-sm-10">
      <textarea rows="3" class="am-input-sm" placeholder="输入备注" name="remark" id="remark"></textarea>
    </div>
  </div>
</form>
<script>
  //添加按钮显示
  var buttonIndex = 1;
  function addButton(){
    $('#buttonArea tbody').append(' <tr>'+
            '<td style="width: 40%;"><input class="am-input-sm" type="text" name="button_name_'+buttonIndex+'"/></td>'+
            '<td style="width: 40%;"><input class="am-input-sm" type="text" name="button_code_'+buttonIndex+'"/></td>'+
            '<td><a class="am-badge am-badge-secondary am-radius am-text-lg" onclick="removeButton(this)" >' +
            '   <span class="am-icon-remove"></span>删除</a>' +
            '</td>'+
          '</tr>');
          buttonIndex++;
  }
  //删除目标行
  function removeButton(target){
        $(target).parent().parent().remove();
  }
</script>