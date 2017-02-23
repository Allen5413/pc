<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
  .table-head{padding-right:17px;}
  .table-body{width:100%;overflow-y:scroll;}
  .table-head table,.table-body table{width:100%;}
</style>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="addProductForm" name="addProductForm" method="post">
  <div class="am-panel am-panel-default no-margin-bottom">
    <div class="am-panel-hd">产品信息</div>
    <div class="am-panel-bd am-u-sm-12 no-padding">
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="add_name"><i class="red">*</i>名称</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="text" placeholder="输入名称" required id="add_name" name="name"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="add_code"><i class="red">*</i>编码</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="text" placeholder="输入编码" required id="add_code" name="code"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="add_type"><i class="red">*</i>类别</label>
          <div class="am-u-sm-9">
            <select class="am-input-sm" required id="add_type" data-am-selected="{btnWidth:'226px'}" name="type">
              <c:forEach items="${productTypes}" var="productType">
                <option value="${productType.id}">${productType.name}</option>
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
                <input type="radio" name="selfMade" value="1"  > 是
              </label>
              <label class="am-radio-inline" style="padding-top: 0px;top: 5px;">
                <input type="radio" name="selfMade" value="0" checked> 否
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
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 8px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="product_name"><i class="red">*</i>产品</label>
          <div class="am-u-sm-9">
            <select class="am-input-sm" type="text" id="product_name" data-am-selected="{maxHeight: 500, searchBox: 1,btnWidth:'226px'}">
                <c:forEach items="${products}" var="product">
                  <option value="${product.id}" data_name="${product.name}" data_code ="${product.code}">${product.name}|${product.code}</option>
                </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right" style="margin-top: 10px;">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="add_code"><i class="red">*</i>用量</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="number" step="0.05" required id="quantity" name="quantity"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="add_code">提前期</label>
          <div class="am-u-sm-9">
            <input class="am-input-sm" type="number" min="0" value="0" step="0.1" required id="ahead" name="ahead"  />
          </div>
        </div>
      </div>
      <div class="am-u-sm-6 no-padding-left no-padding-right">
        <div class="am-form-group">
          <label class="am-u-sm-3 am-form-label no-padding-right" for="add_code">层级</label>
          <div class="am-u-sm-5">
            <input class="am-input-sm" type="number" min="1" value="1" step="1" required id="level" name="level"  />
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
        <table id="productDetail" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tbody></tbody>
        </table>
      </div>
    </div>
  </div>
</form>
<script>
$(function(){
  $("input[type='radio']").uCheck();
  $("select").selected();

//绑定详细数据添加
  $('#addProductDetail').on('click',function(){
      if($('#quantity').val().trim().length==0){
        app.msg("请输入用量", 1);
        return false;
      }
      var productSelect = $('#product_name option:selected');
      var code = productSelect.attr("data_code");
      var name = productSelect.attr("data_name");
      var productId = productSelect.val();
      var flag = false;
      $('#productDetail tr').each(function(){
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
      $('#productDetail tbody').append('<tr product-id="'+productId+'">'+
              '<td style="width: 8%;">'+($('#productDetail tr').length+1)+'</td>'+
              '<td style="width: 20%;">'+code+'</td>'+
              '<td style="width: 20%;">'+name+'</td>'+
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