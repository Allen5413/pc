<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
  .table-head{padding-right:17px;}
  .table-body{width:100%;overflow-y:scroll;}
  .table-head table,.table-body table{width:100%;}
</style>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editProductForm" name="editProductForm" method="post">
  <input type="hidden" id="edit_id" value="${productInfo.id}"/>
  <div class="am-panel am-panel-default no-margin-bottom">
    <div class="am-panel-hd">产品信息</div>
    <div class="am-panel-bd am-u-sm-12 no-padding">
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_name"><i class="red">*</i>名称</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="text" placeholder="输入名称" required id="edit_name" name="name"
            value="${productInfo.name}"/>
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_code"><i class="red">*</i>编码</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="text" placeholder="输入编码" required id="edit_code" name="code"
                   value="${productInfo.code}"/>
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_type"><i class="red">*</i>类别</label>
          <div class="am-u-sm-9">
            <select class="am-input-sm" required id="edit_type" name="type">
              <c:forEach items="${productTypes}" var="productType">
                <option value="${productType.id}" <c:if test="${productType.id==productInfo.type}"> selected </c:if>>${productType.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right">自制件</label>
          <div class="am-u-sm-9">
            <div class="am-form-group">
              <label class="am-radio-inline" style="padding-top: 0px;top: 5px;">
                <input type="radio" name="selfMade" value="1" <c:if test="${1==productInfo.selfMade}"> checked </c:if> > 是
              </label>
              <label class="am-radio-inline" style="padding-top: 0px;top: 5px;">
                <input type="radio" name="selfMade" value="0" <c:if test="${0==productInfo.selfMade}"> checked </c:if>> 否
              </label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="am-panel am-panel-default am-u-sm-12 no-padding-left no-padding-right no-margin-bottom">
    <div class="am-panel-hd">产品组成</div>
    <div class="am-panel-bd am-u-sm-12 no-padding">
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_product_name"><i class="red">*</i>产品</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="text"  id="edit_product_name"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_quantity"><i class="red">*</i>用量</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="number" step="0.05" required id="edit_quantity" name="quantity"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_ahead">提前期</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="number" min="0" value="0" step="0.1" required id="edit_ahead" name="ahead"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="edit_level">层级</label>
          <div class="am-u-sm-5">
            <input class="am-input-sm" type="number" min="1" value="1" step="1" required id="edit_level" name="level"  />
          </div>
          <div class="am-u-sm-4">
            <button class="am-btn am-btn-primary am-btn-sm" type="button" id="addProductDetail"><span class="am-icon-plus"></span>&nbsp;确定</button>
          </div>
        </div>
      </div>
      <div class="table-head">
        <table class="am-table am-table-bordered am-table-striped no-margin-bottom" style="width:100%;">
          <tr class="am-primary">
            <th style="width: 8%; text-align: center;">序号</th>
            <th style="width: 20%; text-align: center;">编码</th>
            <th style="width: 20%; text-align: center;">名称</th>
            <th style="width: 13%; text-align: center;">用量</th>
            <th style="width: 15%; text-align: center;">提前期</th>
            <th style="width: 12%; text-align: center;">层级</th>
            <th style="width: 12%; text-align: center;">操作</th>
          </tr>
        </table>
      </div>
      <div class="table-body" style=" height: 110px;">
        <table id="editProductDetail" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tbody>
          <c:forEach items="${productInfo.productSelfUses}" var="productSelfUse" varStatus="index">
           <tr data-id="${productSelfUse.id}">
             <td style="width: 8%;">${index.index+1}</td>
             <td style="width: 20%;">${productSelfUse.code}</td>
             <td style="width: 20%;">${productSelfUse.name}</td>
             <td style="width: 13%;">${productSelfUse.quantity}</td>
             <td style="width: 15%;">${productSelfUse.ahead}</td>
             <td style="width: 12%;">${productSelfUse.level}</td>
             <td style="width: 12%;">
                <a class="am-badge am-badge-danger am-radius am-text-lg" onclick="removeProductDetail(this)" >
                <span class="am-icon-trash-o"></span>&nbsp;删除</a></td>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</form>
<script>
$(function(){
  $("input[type='radio']").uCheck();
  //绑定详细数据添加
  $('#addProductDetail').on('click',function(){
      if($('#quantity').val().trim().length==0){
        app.msg("请输入用量", 1);
        return false;
      }
      var code = $('#product_name').val();
      var flag = false;
      $('#editProductDetail tr').each(function(){
          if($(this).children('td').eq(1).text()==code){
              $(this).children('td').eq(3).text($('#quantity').val());
              $(this).children('td').eq(4).text($('#ahead').val());
              $(this).children('td').eq(5).text($('#level').val());
              flag = true;
              return false;
          }
      });
      if(flag){
          return false;
      }
      $('#editProductDetail tbody').append('<tr>'+
              '<td style="width: 8%;">'+($('#productDetail tr').length+1)+'</td>'+
              '<td style="width: 20%;">'+$('#product_name').val()+'</td>'+
              '<td style="width: 20%;">'+$('#product_name').val()+'</td>'+
              '<td style="width: 13%;">'+$('#quantity').val()+'</td>'+
              '<td style="width: 15%;">'+$('#ahead').val()+'</td>'+
              '<td style="width: 12%;">'+$('#level').val()+'</td>'+
              '<td style="width: 12%;"><a class="am-badge am-badge-danger am-radius am-text-lg" onclick="removeProductDetail(this)" >' +
              '   <span class="am-icon-trash-o"></span>&nbsp;删除</a></td></tr>');
  });
});
function removeProductDetail(obj){
  $(obj).parent().parent().remove();
}
</script>