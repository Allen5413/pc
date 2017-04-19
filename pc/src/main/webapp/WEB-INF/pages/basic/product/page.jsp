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
  <select id="type" name="type" onchange="app.changeSelect(this)">
    <option value=""></option>
    <option value="null">全部</option>
    <c:forEach items="${productTypes}" var="productType">
      <option value="${productType.FCATEGORYID}" <c:if test="${param.type==productType.FCATEGORYID}"> selected </c:if>>${productType.FNAME}</option>
    </c:forEach>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;
  <label>编码：</label>
  <input type="text" id="code" name="code" value="${param.FNUMBER}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <label >名称：</label>
  <input type="text" id="name" name="name" value="${param.FNAME}" />&nbsp;&nbsp;&nbsp;&nbsp;
  <c:if test="${isShowFindBtn}">
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
          data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
          onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-search"></span> 查询</button>
  </c:if>
</form>
<p /><p />

<table class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
  <c:if test="${false&&isShowAddBtn}">
    <tr>
      <td colspan="999" style="background-color:#FFF">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="addProduct()"><span class="am-icon-plus"></span> 新增</button>
      </td>
    </tr>
  </c:if>
  <tr class="am-primary">
    <th style="width: 5%;">序号</th>
    <th style="width: 21%;">编号</th>
    <th style="width: 34%;">名称</th>
    <th style="width: 6%;">顺序号</th>
    <th style="width: 10%;">类型</th>
    <th style="width: 6%;">自制件</th>
    <th style="width: 17%;">操作</th>
  </tr>
  <c:if test="${empty pageInfo || empty pageInfo.pageResults}">
    <tr>
      <td colspan="99" align="center" style="color: red;">没有找到相关数据</td>
    </tr>
  </c:if>
  <c:forEach var="product" items="${pageInfo.pageResults}" varStatus="status">
    <tr>
      <td align="center">${status.index+1}</td>
      <td>${product['FNUMBER']}</td>
      <td>${product['FNAME']}</td>
      <td>${product['FSNO']}</td>
      <td>${product['cateGoryName']}</td>
      <td>${product['FERPCLSID']==1?'是':'否'}</td>
      <td>
        <a class="am-badge am-badge-primary am-radius am-text-lg" onClick="detailProduct(${product['FMATERIALID']})"><span class="am-icon-th-list"></span> 详细</a>
        <c:if test="${isShowEditBtn}">
          <a class="am-badge am-badge-secondary am-radius am-text-lg"
             data-fmaterialid="${product['FMATERIALID']}" data-fsno="${product['FSNO']}" name="showEditFSNO"><span class="am-icon-edit"></span> 顺序号</a>
        </c:if>
      </td>
    </tr>
  </c:forEach>
</table>
<%@ include file="../../common/page.jsp"%>
<script>
 $(function(){
       $("a[name='showEditFSNO']").on('click',function(){
               var id = $(this).attr("data-fmaterialid");
               var fsno = $(this).attr("data-fsno");
               app.openDialog("${pageContext.request.contextPath}/editProduct/open.html?id="+id+"&fsno="+fsno, '设置产品顺序号', 300,180, function(index){
                   app.edit("${pageContext.request.contextPath}/editProduct/editor.json",$('#eidtProductSnoForm').serialize(), index);
               });
       });
 });
  function productSno(id,fsno){
          console.log("${pageContext.request.contextPath}/editProduct/open.html?id="+id+"&fsno="+fsno);

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
          productDetail['selfProductId'] = $(this).attr('product-id');
          productDetails.push(productDetail);
      });
      app.add("${pageContext.request.contextPath}/addProduct/add.json", {'code':code,'name':name,'type':$('#add_type').val(),
              'selfMade':$("#addProductForm input[type='radio']:checked").val(),'productSelfUseList':JSON.stringify(productDetails)}, index);
    });
  }
  function detailProduct(id){
          var url = '${pageContext.request.contextPath}/detailProduct/open.html?id='+id;
          app.openOneBtnDialog(url, '产品详细信息', 700, 560);
  }
  function del(id){
    app.del("您确定要删除该产品类别信息？", "${pageContext.request.contextPath}/delProductType/del.json", {"id":id});
  }
</script>
