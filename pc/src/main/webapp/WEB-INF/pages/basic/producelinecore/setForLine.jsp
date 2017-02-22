<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table width="99%">
  <tr>
    <td width="50%">
      <div class="am-panel am-panel-primary" style="width:99%; height: 466px; margin:10px 0 0 4px;">
        <div class="am-panel-hd am-cf" data-am-collapse="{target: '#with'}">未关联的工作中心：<span class="am-icon-chevron-down am-fr"></span></div>
        <div id="with" class="am-in">
          <table id="notWithTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
            <c:forEach items="${notWithList}" var="workCore">
              <tr onclick="clickNotWithCore(this)">
                <td>
                  [${workCore.code}]  ${workCore.name}
                  <input type="hidden" name="wcIds" value="${workCore.id}" />
                </td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </div>
    </td>
    <td>
      <div class="am-panel am-panel-primary" style="width:99%; height: 466px; margin:10px 4px 0 8px;">
        <div class="am-panel-hd am-cf" data-am-collapse="{target: '#notWith'}">已关联的工作中心：<span class="am-icon-chevron-down am-fr"></span></div>
        <div id="notWith" class="am-in">
          <form class="am-form" id="setForm" name="setForm" method="post">
            <input type="hidden" name="plId" value="${param.plId}" />
            <table id="withTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
              <c:set var="withListLength" value="${fn:length(withList)}" />
              <c:forEach items="${withList}" var="workCore" varStatus="status">
                <tr>
                  <td onclick="clickWithCore(this)">
                    [${workCore.code}]  ${workCore.name}
                    <input type="hidden" name="wcIds" value="${workCore.id}" />
                  </td>
                  <td width="40%">
                    <c:if test="${0 == status.index}">
                      <a class="am-badge am-badge-disabled am-radius am-text-lg"><span class="am-icon-upload"></span> 上移</a>
                    </c:if>
                    <c:if test="${0 < status.index}">
                      <a class="am-badge am-badge-success am-radius am-text-lg" onClick="up(this)"><span class="am-icon-upload"></span> 上移</a>
                    </c:if>
                    <c:if test="${withListLength == status.index+1}">
                      <a class="am-badge am-badge-disabled am-radius am-text-lg"><span class="am-icon-download"></span> 下移</a>
                    </c:if>
                    <c:if test="${withListLength > status.index+1}">
                      <a class="am-badge am-badge-secondary am-radius am-text-lg" onClick="down(this)"><span class="am-icon-download"></span> 下移</a>
                    </c:if>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </form>
        </div>
      </div>
    </td>
  </tr>
</table>
<script>
  function clickNotWithCore(objTr){
    var html = $(objTr).find("td").html();
    $(objTr).remove();

    var table = $("#withTable");
    var tr = $("<tr></tr>");
    var td = $("<td onclick='clickWithCore(this)'></td>");
    var td2 = $("<td width='40%'></td>");
    td.append(html);
    tr.append(td).append(td2);
    table.append(tr);

    var firstTr = $("#withTable").find("tr:first");
    var lastTr = $("#withTable").find("tr:last");
    var td = $(firstTr).find("td")[1];
    var td2 = $(lastTr).find("td")[1];
    $(td).html("");
    $(td2).html("");
    if($(firstTr).html() == $(lastTr).html()){
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-download'></span> 下移</a>");
    }else{
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");
      $(td2).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td2).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-download'></span> 下移</a>");
      var trCount = getWithTableTrCount();
      if(2 < trCount) {
        //倒数第二个的下移启用
        var td3 = $(lastTr).prev().find("td")[1];
        $(td3).html("");
        $(td3).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='down(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
        $(td3).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='up(this)'><span class='am-icon-download'></span> 下移</a>");
      }
    }

  }

  function clickWithCore(objTd){
    var html = $(objTd).html();
    $(objTd).parent().remove();

    var table = $("#notWithTable");
    var tr = $("<tr onclick='clickNotWithCore(this)'></tr>");
    var td = $("<td></td>");
    td.append(html);
    tr.append(td);
    table.append(tr);

    var firstTr = $("#withTable").find("tr:first");
    var lastTr = $("#withTable").find("tr:last");
    var td = $(firstTr).find("td")[1];
    var td2 = $(lastTr).find("td")[1];
    $(td).html("");
    $(td2).html("");
    if($(firstTr).html() == $(lastTr).html()){
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-download'></span> 下移</a>");
    }else{
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");
      $(td2).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td2).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-download'></span> 下移</a>");
    }
  }

  function up(obj){
    //得到当前tr的上一个tr
    var prevTr = getPrevTr(obj);
    var prevTrTemp = prevTr;

    //上移tr的内容
    var upTrHtml = $(obj).parent().parent().html();
    //下移tr的内容
    var downTrHtml = $(prevTr).html();

    //删除上一个tr
    $(prevTr).remove();
    //在当前tr后面追加上一个tr
    $(obj).parent().parent().after(prevTrTemp);

    //得到table的第一个tr的内容
    var firstTrHtml = $("#withTable").find("tr:first").html();
    //得到table的最后一个tr的内容
    var lastTrHtml = $("#withTable").find("tr:last").html();


    //如果上移过后成为第一个tr，就要禁用上移按钮；之前第一个tr被移动到下面，就要启用上移按钮
    if(upTrHtml == firstTrHtml){
      var firstTr = $("#withTable").find("tr:first");
      var td = $(firstTr).find("td")[1];
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");

      td = $(prevTrTemp).find("td")[1];
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");
    }

    //如果下移过后成为最后一个tr，就要禁用下移按钮；之前最后一个tr被移动到上面，就要启用下移按钮
    if(downTrHtml == lastTrHtml){
      var lastTr = $("#withTable").find("tr:last");
      var td = $(lastTr).find("td")[1];
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-download'></span> 下移</a>");

      td = $(obj).parent();
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");
    }
  }

  function down(obj){
    //得到当前tr的下一个tr
    var nextTr = getNextTr(obj);
    var nextTrTemp = nextTr;

    //下移tr的内容
    var downTrHtml = $(obj).parent().parent().html();
    //上移tr的内容
    var upTrHtml = $(nextTr).html();

    //删除下一个tr
    $(nextTr).remove();
    //在当前tr前面追加下一个tr
    $(obj).parent().parent().before(nextTrTemp);


    //得到table的第一个tr的内容
    var firstTrHtml = $("#withTable").find("tr:first").html();
    //得到table的最后一个tr的内容
    var lastTrHtml = $("#withTable").find("tr:last").html();

    //如果下移过后成为最后一个tr，就要禁用下移按钮；之前最后一个tr被移动到上面，就要启用下移按钮
    if(downTrHtml == lastTrHtml){
      var lastTr = $("#withTable").find("tr:last");
      var td = $(lastTr).find("td")[1];
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-download'></span> 下移</a>");

      td = $(nextTrTemp).find("td")[1];
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");
    }

    //如果上移过后成为第一个tr，就要禁用上移按钮；之前第一个tr被移动到下面，就要启用上移按钮
    if(upTrHtml == firstTrHtml){
      var firstTr = $("#withTable").find("tr:first");
      var td = $(firstTr).find("td")[1];
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-disabled am-radius am-text-lg'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");

      td = $(obj).parent();
      $(td).html("");
      $(td).append("<a class='am-badge am-badge-success am-radius am-text-lg' onClick='up(this)'><span class='am-icon-upload'></span> 上移</a>&nbsp;");
      $(td).append("<a class='am-badge am-badge-secondary am-radius am-text-lg' onClick='down(this)'><span class='am-icon-download'></span> 下移</a>");
    }
  }

  //得到已关联表格的tr数量
  function getWithTableTrCount(){
    return $("#withTable").find("tr").length;
  }

  //得到已关联表格的上一个tr
  function getPrevTr(obj){
    return $(obj).parent().parent().prev();
  }

  //得到已关联表格的下一个tr
  function getNextTr(obj){
    return $(obj).parent().parent().next();
  }
</script>