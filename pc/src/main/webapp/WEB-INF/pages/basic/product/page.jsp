<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findProductPage/find.html" method="post">
  <input type="hidden" id="rows" name="rows" />
  <input type="hidden" id="currentPage" name="page" value="${pageInfo.currentPage}"/>
  <input type="hidden" name="resourceId" value="${requestScope.resourceId}" />

  <c:set var="isShowAddBtn" value="${my:isPermission(requestScope.resourceId,'addProduct',sessionScope.menuMap)}" />
  <c:set var="isShowEditBtn" value="${my:isPermission(requestScope.resourceId,'editProduct',sessionScope.menuMap)}" />
  <c:set var="isShowFindBtn" value="${my:isPermission(requestScope.resourceId,'find',sessionScope.menuMap)}" />
  <label >类别：</label>
  <select id="type" name="type">
    <option value="-1">全部</option>
    <c:forEach items="${productTypes}" var="productType">
      <option value="${productType.id}" <c:if test="${param.type==productType.id}"> selected </c:if>>${productType.name}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
  <label>编码：</label>
  <input type="text" id="code" name="code" value="${param.code}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >名称：</label>
  <input type="text" id="name" name="name" value="${param.name}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${isShowFindBtn}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
          data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
          onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
  </c:if>
</form>
<p /><p />

<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <c:if test="${isShowAddBtn}">
    <tr>
      <td colspan="999" style="background-color:#FFF">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="addProduct()"><span class="am-icon-plus"></span> 新增</button>
      </td>
    </tr>
  </c:if>
  <tr class="am-primary">
    <th style="width: 5%;">序号</th>
    <th style="width: 15%;">编号</th>
    <th style="width: 20%;">名称</th>
    <th style="width: 10%;">类型</th>
    <th style="width: 10%;">操作人</th>
    <th style="width: 15%;">操作时间</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="product" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${product['code']}</td>
      <td>${product['name']}</td>
      <td>${product['tName']}</td>
      <td>${product['creator']}</td>
      <td><fmt:formatDate value="${product['operate_time']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
      <td>
        <c:if test="${isShowEditBtn}">
          <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="editProduct(${product['id']})"><span class="am-icon-edit"></span> 修改</a>
        </c:if>
        <c:if test="${isShowDelBtn}">
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${product['id']})"><span class="am-icon-trash-o"></span> 删除</a>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>

  function editProduct(id){
    var url = '${pageContext.request.contextPath}/editProduct/open.html?id='+id;
    app.openDialog(url, '编辑产品信息', 700, 560, function(index){
      var code = $("#edit_code").val().trim();
      var name = $("#edit_name").val().trim();
      if(code == ""){
        app.msg("请输入编号", 1);
        return;
      }
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      var productDetails = [];
      //获取产品组成信息
      $('#editProductDetail tr').each(function(){
              var productDetail = {};
              productDetail['code'] = $(this).children('td').eq(1).text();
              productDetail['name'] = $(this).children('td').eq(2).text();
              productDetail['quantity'] = $(this).children('td').eq(3).text();
              productDetail['ahead'] = $(this).children('td').eq(4).text();
              productDetail['level'] = $(this).children('td').eq(5).text();
              productDetails.push(productDetail);
              });
      app.edit("${pageContext.request.contextPath}/editProduct/editor.json", {'code':code,'name':name,'type':$('#edit_type').val(),
              'selfMade':$("#editProductForm input[type='radio']:checked").val(),'id':$('#edit_id').val(),
              'productSelfUseList':JSON.stringify(productDetails)}, index);
    });
  }

  function addProduct(){
    app.openDialog("${pageContext.request.contextPath}/addProduct/open.html", "新添加产品", 700, 560, function(index){
      var code = $("#add_code").val().trim();
      if(code == ""){
        app.msg("请输入编号", 1);
        return;
      }
      var name = $("#add_name").val().trim();
      if(name == ""){
        app.msg("请输入名称", 1);
        return;
      }
      var productDetails = [];
      //获取产品组成信息
      $('#productDetail tr').each(function(){
          var productDetail = {};
          productDetail['code'] = $(this).children('td').eq(1).text();
          productDetail['name'] = $(this).children('td').eq(2).text();
          productDetail['quantity'] = $(this).children('td').eq(3).text();
          productDetail['ahead'] = $(this).children('td').eq(4).text();
          productDetail['level'] = $(this).children('td').eq(5).text();
          productDetails.push(productDetail);
      });
      app.add("${pageContext.request.contextPath}/addProduct/add.json", {'code':code,'name':name,'type':$('#add_type').val(),
              'selfMade':$("#addProductForm input[type='radio']:checked").val(),'productSelfUseList':JSON.stringify(productDetails)}, index);
    });
  }

  function del(id){
    app.del("您确定要删除该产品类别信息？", "${pageContext.request.contextPath}/delProductType/del.json", {"id":id});
  }
</script>
