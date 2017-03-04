<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
  .table-head{padding-right:17px;}
  .table-body{width:100%;overflow-y:scroll;}
  .table-head table,.table-body table{width:100%;}
</style>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="editProductForm" name="editProductForm" method="post">
  <div class="am-panel am-panel-default no-margin-bottom">
    <div class="am-panel-hd">产品信息</div>
    <div class="am-panel-bd am-u-sm-12 no-padding">
      <table class="am-table am-table-bordered  no-margin-bottom" style="width:100%;">
        <tbody>
          <tr>
            <th style="width: 10%;">名称</td>
            <td style="width: 40%;">${productInfo['FNAME']}</td>
            <th style="width: 10%;">编码</th>
            <td style="width: 40%;">${productInfo['FNUMBER']}</td>
          </tr>
          <tr>
            <th style="width: 10%;">类型</td>
            <td style="width: 40%;">${productInfo['cateGoryName']}</td>
            <th style="width: 10%;">自制件</th>
            <td style="width: 40%;">${productInfo['FERPCLSID']==1?"是":"否"}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="am-panel am-panel-default am-u-sm-12 no-padding-left no-padding-right no-margin-bottom">
    <div class="am-panel-hd">产品组成</div>
    <div class="am-panel-bd am-u-sm-12 no-padding">
      <div class="table-head" style="margin-top: 8px;">
        <table class="am-table am-table-bordered am-table-striped no-margin-bottom" style="width:100%;">
          <tr class="am-primary">
            <th style="width: 8%; text-align: center;">序号</th>
            <th style="width: 23%; text-align: center;">编码</th>
            <th style="width: 22%; text-align: center;">名称</th>
            <th style="width: 18%; text-align: center;">用量</th>
            <th style="width: 15%; text-align: center;">提前期</th>
            <th style="width: 8%; text-align: center;">层级</th>
          </tr>
        </table>
      </div>
      <div class="table-body" style=" height: 243px;">
        <table id="editProductDetail" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tbody>
          <c:forEach items="${productInfo['selfMades']}" var="productSelfUse" varStatus="index">
           <tr>
             <td style="width: 8%;">${index.index+1}</td>
             <td style="width: 23%;">${productSelfUse['FNUMBER']}</td>
             <td style="width: 22%;">${productSelfUse['FNAME']}</td>
             <td style="width: 18%;">${productSelfUse['useQty']}</td>
             <td style="width: 15%;">${productSelfUse['FOFFSETTIME']}</td>
             <td style="width: 8%;">${productSelfUse['FSEQ']}</td>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</form>
