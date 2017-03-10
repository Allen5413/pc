<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form id="setProductCgForm" name="setProductCgForm" method="post">
  <table id="cgTable" class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
    <input type="hidden" id="delPlcpcgIds" name="delPlcpcgIds" />
    <input type="hidden" name="plcpId" value="${param.plcpId}" />
    <tr>
      <td colspan="999" style="background-color:#FFF">
        <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="addProductCG()"><span class="am-icon-plus"></span> 新增班组</button>
      </td>
    </tr>
    <tr class="am-primary" style="border-right: 0px;">
      <th style="width: 20%; ">名称</th>
      <th style="width: 20%; ">工作模式</th>
      <th style="width: 10%;">序号</th>
      <th style="width: 14%;">产能（小时）</th>
      <th style="width: 14%;">最小批量</th>
      <th>操作</th>
    </tr>
    <c:forEach var="plcpcg" items="${withCgList}" varStatus="status">
      <tr>
        <input type="hidden" name="plcpcgIds" value="${plcpcg.id}" />
        <td>
          <select id="cgId${status.index}" name="cgIds" data-am-selected="{btnWidth: '120px', maxHeight: '180px', searchBox: 1}">
            <c:forEach var="classGroup" items="${classGroupList}">
              <option value="${classGroup.id}" <c:if test="${plcpcg.class_group_id eq classGroup.id}">selected</c:if> >${classGroup.name}</option>
            </c:forEach>
          </select>
        </td>
        <td>
          <select id="wmId${status.index}" name="wmIds" data-am-selected="{btnWidth: '120px', maxHeight: '180px', searchBox: 1}">
            <c:forEach var="workMode" items="${workModeList}">
              <option value="${workMode.id}" <c:if test="${plcpcg.work_mode_id eq workMode.id}">selected</c:if> >${workMode.name}</option>
            </c:forEach>
          </select>
        </td>
        <td>
          <input name="snos" value="${plcpcg.sno}" style='width:50px;'/>
        </td>
        <td>
          <input name="unitTimeCapacitys" value="${plcpcg.unit_time_capacity / 3600}" style='width:60px;'/>
        </td>
        <td>
          <input name="minBatchs" value="${plcpcg.min_batch}" style='width:60px;'/>
        </td>
        <td>
          <a class="am-badge am-badge-danger am-radius am-text-lg" onClick="del(${plcpcg.id}, this)"><span class="am-icon-trash-o"></span> 删除</a>
        </td>
      </tr>
    </c:forEach>
  </table>
</form>
<script>
  $("select").selected();

  var num = ${fn:length(withCgList)};
  function addProductCG(){
    var table = $("#cgTable");
    var tr = $("<tr><input type='hidden' name='plcpcgIds' value='0' /></tr>");
    var td = $("<td></td>");
    var tdHtml = "<select id=\"cgId${status.index}"+num+"\" name=\"cgIds\" data-am-selected=\"{btnWidth: '120px', maxHeight: '180px', searchBox: 1}\">";
    <c:forEach var="classGroup" items="${classGroupList}">
      tdHtml += "<option value=\"${classGroup.id}\">${classGroup.name}</option>";
    </c:forEach>
      tdHtml += "</select>";
    td.append(tdHtml);
    var td2 = $("<td></td>");
    var td2Html = "<select id=\"wmId${status.index}"+num+"\" name=\"wmIds\" data-am-selected=\"{btnWidth: '120px', maxHeight: '180px', searchBox: 1}\">";
    <c:forEach var="workMode" items="${workModeList}">
    td2Html += "<option value=\"${workMode.id}\">${workMode.name}</option>";
    </c:forEach>
    td2Html += "</select>";
    td2.append(td2Html);
    var td3 = $("<td><input name='snos' style='width:50px;'/></td>");
    var td4 = $("<td><input name='unitTimeCapacitys' style='width:60px;'/></td>");
    var td5 = $("<td><input name='minBatchs' style='width:60px;'/></td>");
    var td6 = $("<td><a class='am-badge am-badge-danger am-radius am-text-lg' onClick='del(0, this)'><span class='am-icon-trash-o'></span> 删除</a></td>");
    tr.append(td).append(td2).append(td3).append(td4).append(td5).append(td6);
    table.append(tr);
    num ++;
    $("select").selected();
  }

  function del(plcpcgId, obj){
    app.confirm("您确定要删除该班组？", function() {
      if (plcpcgId > 0) {
        $("#delPlcpcgIds").val($("#delPlcpcgIds").val() + plcpcgId + ",");
      }
      $(obj).parent().parent().remove();
    });
  }
</script>