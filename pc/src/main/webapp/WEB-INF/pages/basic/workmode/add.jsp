<%@ page language="java" contentType="text/html; charset=utf-8"
                  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
  .table-head{padding-right:17px;}
  .table-body{width:100%;overflow-y:scroll;}
  .table-head table,.table-body table{width:100%;}
  label{
    margin-top: 4px;;
  }
</style>
<form class="am-form am-form-horizontal" id="addForm" name="addForm" method="post">
  <div class="am-u-sm-4 no-padding">
    <div class="am-g am-margin-top">
      <input type="hidden" id="add_workTime" name="workTimeIds"/>
      <div class="am-u-sm-4 am-text-right no-padding-left no-padding-right"><label ><i class="red">*</i>班次：</label></div>
      <div class="am-u-sm-8 no-padding-left no-padding-right">
        <select class="am-input-sm" id="add_workTimes"  required data-am-selected="{maxHeight: 100,btnWidth:'117px'}">
          <option value="-1">选择班次</option>
          <c:forEach items="${workTimes}" var="workTime">
            <option value="${workTime.id}" data-code="${workTime.code}" data-start="${workTime.beginTimeStr}"
                    data-end="${workTime.endTimeStr}" data-sub="${workTime.timeSub}">${workTime.name}</option>
          </c:forEach>
        </select>
      </div>
    </div>
  </div>
  <div class="am-u-sm-4 no-padding">
    <div class="am-g am-margin-top">
      <div class="am-u-sm-4 am-text-right no-padding-left no-padding-right"><label ><i class="red">*</i>编号：</label></div>
      <div class="am-u-sm-8 no-padding-left no-padding-right">
        <input class="am-input-sm" type="text" placeholder="输入编号" required id="add_code" name="code"  />
      </div>
    </div>
  </div>
  <div class="am-u-sm-4 no-padding">
    <div class="am-g am-margin-top">
      <div class="am-u-sm-4 am-text-right no-padding-left no-padding-right"><label ><i class="red">*</i>名称：</label></div>
      <div class="am-u-sm-8 no-padding-left">
        <input class="am-input-sm" type="text" placeholder="输入名称" required id="add_name" name="name"  />
      </div>
    </div>
  </div>

  <div class="am-u-sm-12 no-padding">
    <div class="table-head" style="padding-top: 20px;">
      <table class="am-table am-table-bordered am-table-striped no-margin-bottom" style="width:100%;">
        <tr class="am-primary">
          <th style="width: 8%; text-align: center;">序号</th>
          <th style="width: 19%; text-align: center;">编码</th>
          <th style="width: 19%; text-align: center;">名称</th>
          <th style="width: 13%; text-align: center;">开始时间</th>
          <th style="width: 13%; text-align: center;">结束时间</th>
          <th style="width: 13%; text-align: center;">单班时间</th>
          <th style="width: 15%; text-align: center;">操作</th>
        </tr>
      </table>
    </div>
    <div class="table-body" style=" height: 138px;">
      <table id="workTimeDetail" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
        <tbody></tbody>
      </table>
    </div>
  </div>

</form>
<script type="text/javascript">
  $(function(){
          $("select").selected();
          $('#add_workTimes').on('change',function(){
                  if($(this).val()=='-1'){
                          return false;
                          }
                  var option = $(this).find(":selected");
                  var flag = false;
                  $('#workTimeDetail tr').each(function(){
                          if($(this).attr('work-time-id')==$(option).val()){
                                  flag = true;
                                  return false;
                                  }
                          });
                  if(flag){
                          return false;
                          }
                  $('#workTimeDetail tbody').append('<tr work-time-id="'+$(this).val()+'">'+
                          '<td style="width: 8%;">'+($('#workTimeDetail tr').length+1)+'</td>'+
                          '<td style="width: 19%;">'+$(option).attr("data-code")+'</td>'+
                          '<td style="width: 19%;">'+$(option).text()+'</td>'+
                          '<td style="width: 13%;">'+$(option).attr("data-start")+'</td>'+
                          '<td style="width: 13%;">'+$(option).attr("data-end")+'</td>'+
                          '<td style="width: 13%;">'+$(option).attr("data-sub")+'</td>'+
                          '<td style="width: 15%;"><a class="am-badge am-badge-danger am-radius am-text-lg" onclick="removeProductDetail(this)" >' +
                          '   <span class="am-icon-trash-o"></span>&nbsp;删除</a></td></tr>');
                  });
          });
          function removeProductDetail(obj){
                  $(obj).parent().parent().remove();
                  }
</script>