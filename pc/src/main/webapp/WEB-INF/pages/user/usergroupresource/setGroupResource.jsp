<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="setForm" name="setForm" method="post">
  <table width="99%">
    <tr>
      <td>
        <div class="am-panel am-panel-primary" style="width:99%; height: 466px; margin:10px 0 0 4px;">
          <div class="am-panel-hd am-cf" data-am-collapse="{target: '#with'}">未关联的工作组：<span class="am-icon-chevron-down am-fr"></span></div>
          <div id="with" class="am-in">
            <table id="notWithTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
              <c:forEach items="${notWithList}" var="workGroup">
                <tr onclick="selectGroup(0, this)">
                  <td>[${workGroup.code}]  ${workGroup.name}</td>
                </tr>
              </c:forEach>
            </table>
          </div>
        </div>
      </td>
      <td>
        <div class="am-panel am-panel-primary" style="width:99%; height: 466px; margin:10px 4px 0 8px;">
          <div class="am-panel-hd am-cf" data-am-collapse="{target: '#notWith'}">已关联的工作组：<span class="am-icon-chevron-down am-fr"></span></div>
          <div id="notWith" class="am-in">
            <table id="withTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
              <c:forEach items="${withList}" var="workGroup">
                <tr onclick="selectGroup(1, this)">
                  <td>[${workGroup.code}]  ${workGroup.name}</td>
                </tr>
              </c:forEach>
            </table>
          </div>
        </div>
      </td>
    </tr>
  </table>
</form>
<script>
  function selectGroup(flag, objTr){
    $(objTr).remove();
    var selectHtml = $(objTr).html();
    var table;
    if(flag == 0){
      table = $("#withTable");
    }else{
      table = $("#notWithTable");
    }
    var tr = $("<tr onclick='selectGroup("+(0 == flag ? 1 : 0)+", this)'></tr>");
    var td = $("<td>"+selectHtml+"</td>");
    tr.append(td);
    table.append(tr);
  }
</script>