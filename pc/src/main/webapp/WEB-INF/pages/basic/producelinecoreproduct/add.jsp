<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form id="setCoreProductForm" name="setCoreProductForm" method="post">
  <table id="productTable" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
    <input type="hidden" id="delPlcpIds" name="delPlcpIds" />
    <input type="hidden" name="plId" value="${param.plId}" />
    <input type="hidden" name="wcId" value="${param.wcId}" />
    <tr>
      <td colspan="999" style="background-color:#FFF">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="addCoreProduct()"><span class="am-icon-plus"></span> 新增产品</button>
      </td>
    </tr>
    <tr class="am-primary" style="border-right: 0px;">
      <th style="width:50%; ">产品名称</th>
      <th style="width: 8%;">合格率(%)</th>
      <th>操作</th>
    </tr>
    <c:forEach var="product" items="${withProductList}" varStatus="status">
      <tr>
        <input type="hidden" name="plcpIds" value="${product.plcpId}" />
        <td>
          <select id="pId${status.index}" name="pIdsForAddP" data-am-selected="{btnWidth: '370px', maxHeight: '180px', searchBox: 1}">
            <c:forEach var="product2" items="${productList}">
              <option value="${product2['FMATERIALID']}" <c:if test="${product.FMATERIALID eq product2['FMATERIALID']}">selected</c:if> >[${product2['FNUMBER']}]${product2['FNAME']}</option>
            </c:forEach>
          </select>
        </td>
        <td>
          <input name="qualifiedRates" value="${product.qualifiedRate}"/>
        </td>
        <td>
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${product.plcpId}, this)"><span class="am-icon-trash-o"></span> 删除</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</form>
<script>
  $("select").selected();

  var num = ${fn:length(withProductList)};
  function addCoreProduct(){
    var table = $("#productTable");
    var tr = $("<tr><input type='hidden' name='plcpIds' value='0' /></tr>");
    var td = $("<td></td>");
    var tdHtml = "<select id=\"pId${status.index}"+num+"\" name=\"pIdsForAddP\" data-am-selected=\"{btnWidth: '370px', maxHeight: '180px', searchBox: 1}\">";
    <c:forEach var="product2" items="${productList}">
      tdHtml += "<option value=\"${product2['FMATERIALID']}\">[${product2['FNUMBER']}]${product2['FNAME']}</option>";
    </c:forEach>
      tdHtml += "</select>";
    td.append(tdHtml);
    var td2 = $("<td><input name='qualifiedRates'/></td>");
    var td3 = $("<td><a class='am-badge am-badge-danger am-radius am-text-lg' onClick='del(0, this)'><span class='am-icon-trash-o'></span> 删除</a></td>");
    tr.append(td).append(td2).append(td3);
    table.append(tr);
    num ++;
    $("select").selected();
  }

  function del(plcpId, obj){
    if(plcpId > 0) {
      $("#delPlcpIds").val($("#delPlcpIds").val() + plcpId + ",");
    }
    $(obj).parent().parent().remove();
  }
</script>