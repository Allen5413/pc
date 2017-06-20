<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<p />
<form id="pageForm" name="pageForm" action="${pageContext.request.contextPath}/findSchedul/find.html" method="post">
  <input type="hidden" name="resourceId" value="${requestScope.resourceId}" />
  <c:set var="isShowFindBtn" value="${my:isPermission(requestScope.resourceId,'find',sessionScope.menuMap)}" />

  <table width="70%">
    <tr style="height: 50px;">
      <td style="text-align: right">开始时间：</td>
      <td><input type="text" id="start" name="startDate" value="${param.startDate}" style="height: 30px;"
                 onfocus="WdatePicker({firstDayOfWeek:1})" class="Wdate" /></td>
      <td style="text-align: right">结束时间：</td>
      <td>
        <input type="text" id="end" name="endDate" value="${param.endDate}" style="height: 30px;"
               onfocus="WdatePicker({firstDayOfWeek:1})" class="Wdate" />
      </td>
      <td style="text-align: right">产品名称：</td>
      <td><input type="text" name="name" value="${param.name}"></td>
    </tr>
    <tr style="height: 50px;">
      <td style="text-align: right">工作组：</td>
      <td>
        <select id="wgId" name="wgId" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
          <option value=""></option>
          <option value="null">全部</option>
          <c:forEach var="workGroup" items="${wgList}">
            <option value="${workGroup.id}" <c:if test="${param.wgId eq workGroup.id}">selected="selected" </c:if> >${workGroup.name}</option>
          </c:forEach>
        </select>
      </td>
      <td style="text-align: right">工作中心：</td>
      <td>
        <select id="wcId" name="wcCode" data-am-selected="{maxHeight: 500, searchBox: 1}" onchange="app.changeSelect(this)">
          <option value=""></option>
          <option value="null">全部</option>
          <c:forEach var="workCore" items="${wcList}">
            <option value="${workCore.code}" <c:if test="${param.wcCode eq workCore.code}">selected="selected" </c:if> >${workCore.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr style="height: 50px;">
      <td>
        <c:if test="${isShowFindBtn}">
          &nbsp;&nbsp;&nbsp;&nbsp;
          <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
                  data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '查询超时'}"
                  onclick="searchForm()"><span class="am-icon-search"></span> 查询</button>
        </c:if>
      </td>
    </tr>
  </table>
</form>
<p /><p />
<div style="overflow: auto; width: 100%;">
  <div class="scroll-content" style="width:3000px; height: 700px;">
    <table id="findProductCgTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%; overflow: auto">
      <tr class="am-primary" style="border-right: 0px;">
        <th style="width:5%;" rowspan="4">物料名称</th>
        <th style="width:5%;" rowspan="4">物料编码</th>
        <th style="width:3%;" rowspan="4">客户需求</th>
        <th style="width:3%;" rowspan="4">初期库存</th>
      </tr>
      <tr class="am-primary" style="border-right: 0px;">
        <c:forEach items="${dateMap}" var="entry">
          <th colspan="5"><c:out value="${entry.key}" /></td>
        </c:forEach>
      </tr>
      <tr class="am-primary" style="border-right: 0px;">
        <c:forEach items="${dateMap}" var="entry">
          <th  colspan="5"><c:out value="${entry.value}" /></td>
        </c:forEach>
      </tr>
      <tr class="am-primary" style="border-right: 0px;">
        <c:forEach items="${dateMap}" var="entry">
          <th>生产数量</td>
          <th>工作中心</td>
          <th>班次</td>
          <th>时间</td>
          <th>时长</td>
        </c:forEach>
      </tr>
      <c:forEach var="data" items="${resultList}">
        <%--先循环这个产品某一天中工作中心最多的数量，用来控制用几个tr--%>
        <c:forEach begin="1" end="${data.maxNum}" var="num">
          <tr>
            <td>${data.productName}</td>
            <td>${data.productCode}</td>
            <td>${data.customerNum}</td>
            <td>${data.stock}${data.cgListMap.value}</td>
            <c:forEach items="${data.cgListMap}" var="cg">
              <c:if test="${empty cg.value}">
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
              </c:if>
              <c:if test="${!empty cg.value}">
                <%--因为每天的工作中心数量可能不一样，就要判断每次只显示1个，所以要加个变量i来判断循环到第几次了--%>
                <%--flag是用来标记当天是否已经显示过了，如果没有多的中心了，该天就不会显示，直接放5个空td就行了--%>
                <c:set var="i" value="1" />
                <c:set var="flag" value="0"/>
                <c:forEach items="${cg.value}" var="cg">
                  <c:if test="${num == i}">
                    <td><a href="#" onclick="editCapacity(${cg.id})">${cg.num}</a></td>
                    <td><a href="#" onclick="editWorkCore(${cg.id})">${cg.cgName}</a></td>
                    <td><a href="#" onclick="editWorkClass(${cg.id})">${cg.wtName}</a></td>
                    <td>${cg.time}</td>
                    <td>${cg.hour}</td>
                    <c:set var="flag" value="1"/>
                  </c:if>
                  <c:set var="i" value="${i+1}" />
                </c:forEach>
                <c:if test="${flag == 0}">
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                </c:if>
              </c:if>
            </c:forEach>
          </tr>
        </c:forEach>
      </c:forEach>
    </table>
  </div>
</div>
<script>
  $("select").selected();

  function searchForm(){
    var startDate = $("#start").val();
    var endDate = $("#end").val();
    if(startDate == ""){
      app.msg("请选择开始时间", 1);
      return;
    }
    if(endDate == ""){
      app.msg("请选择结束时间", 1);
      return;
    }
    app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)
  }

  function editCapacity(id){
    var url = '${pageContext.request.contextPath}/editSchedulCapacity/open.html?id='+id;
    app.openDialog(url, '编辑生产数量', 450, 200, function(index){
      var count = $("#count").val().trim();
      if(count == ""){
        app.msg("请输入生产数量", 1);
        return;
      }
      app.edit("${pageContext.request.contextPath}/editSchedulCapacity/editor.json", $('#editForm').serialize(), index);
    });
  }

  function editWorkCore(id){
    var url = '${pageContext.request.contextPath}/editSchedulWC/open.html?id='+id;
    app.openDialog(url, '编辑工作中心', 500, 400, function(index){
      var code = $("#code").val().trim();
      if(code == ""){
        app.msg("请选择工作中心", 1);
        return;
      }
      $("#name").val($("#code").find("option:selected").text());
      app.edit("${pageContext.request.contextPath}/editSchedulWC/editor.json", $('#editForm').serialize(), index);
    });
  }

  function editWorkClass(id){
    var url = '${pageContext.request.contextPath}/editSchedulWClass/open.html?id='+id;
    app.openDialog(url, '编辑班次', 450, 400, function(index){
      var code = $("#code").val().trim();
      if(code == ""){
        app.msg("请选择班次", 1);
        return;
      }
      $("#name").val($("#code").find("option:selected").text());
      app.edit("${pageContext.request.contextPath}/editSchedulWClass/editor.json", $('#editForm').serialize(), index);
    });
  }
</script>
