<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
  <tr class="am-primary" style="border-right: 0px;">
    <th style="width:60%; ">产品名称</th>
    <th style="width: 15%;">合格率</th>
    <th>操作</th>
  </tr>
  <c:forEach var="product" items="${withProductList}" varStatus="status">
    <tr>
      <td>
        <select id="pId${status.index}" name="pId" data-am-selected="{btnWidth: '200px', searchBox: 1}">
          <c:forEach var="product2" items="${productList}">
            <option value="${product2['FMATERIALID']}">[${product2['FNUMBER']}]${product2['FNAME']}</option>
          </c:forEach>
        </select>
      </td>
      <td>
        <input value="${product.qualifiedRate}"/>
      </td>
      <td>
        ss
      </td>
    </tr>
  </c:forEach>
</table>
<script>
  $("select").selected();
</script>