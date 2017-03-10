<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form id="setCoreForm" name="setCoreForm" method="post">
  <table id="coreTable" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
    <input type="hidden" id="delPlcIds" name="delPlcIds" />
    <input type="hidden" name="plId" value="${param.plId}" />
    <tr>
      <td colspan="999" style="background-color:#FFF">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="addCore()"><span class="am-icon-plus"></span> 新增工作中心</button>
      </td>
    </tr>
    <tr class="am-primary" style="border-right: 0px;">
      <th style="width:50%; ">名称</th>
      <th style="width: 8%;">序号</th>
      <th>操作</th>
    </tr>
    <c:forEach var="workCore" items="${withWcList}" varStatus="status">
      <tr>
        <input type="hidden" name="plcIds" value="${workCore.plcId}" />
        <td>
          <select id="wcId${status.index}" name="wcIds" data-am-selected="{btnWidth: '370px', maxHeight: '180px', searchBox: 1}">
            <c:forEach var="workCore2" items="${workCoreList}">
              <option value="${workCore2.id}" <c:if test="${workCore.id eq workCore2.id}">selected</c:if> >[${workCore2.code}]${workCore2.name}</option>
            </c:forEach>
          </select>
        </td>
        <td>
          <input name="snos" value="${workCore.sno}"/>
        </td>
        <td>
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${workCore.plcId}, this)"><span class="am-icon-trash-o"></span> 删除</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</form>
<script>
  $("select").selected();

  var num = ${fn:length(withWcList)};
  function addCore(){
    var table = $("#coreTable");
    var tr = $("<tr><input type='hidden' name='plcIds' value='0' /></tr>");
    var td = $("<td></td>");
    var tdHtml = "<select id=\"wcId${status.index}"+num+"\" name=\"wcIds\" data-am-selected=\"{btnWidth: '370px', maxHeight: '180px', searchBox: 1}\">";
    <c:forEach var="workCore2" items="${workCoreList}">
    tdHtml += "<option value=\"${workCore2.id}\">[${workCore2.code}]${workCore2.name}</option>";
    </c:forEach>
    tdHtml += "</select>";
    td.append(tdHtml);
    var td2 = $("<td><input name='snos'/></td>");
    var td3 = $("<td><a class='am-badge am-badge-danger am-radius am-text-lg' onClick='del(0, this)'><span class='am-icon-trash-o'></span> 删除</a></td>");
    tr.append(td).append(td2).append(td3);
    table.append(tr);
    num ++;
    $("select").selected();
  }

  function del(plcId, obj){
    if(plcId > 0) {
      $("#delPlcIds").val($("#delPlcIds").val() + plcId + ",");
    }
    $(obj).parent().parent().remove();
  }
</script>