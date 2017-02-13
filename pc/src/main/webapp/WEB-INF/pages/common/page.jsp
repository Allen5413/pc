<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<ul id="page" class="am-pagination am-pagination-centered">

    <%--<li class="am-disabled"><a class="prev" href="#" onclick="pageSub('up')">&laquo;</a></li>--%>
    <%--<li <c:if test="${pageInfo.currentPage == 1}">class="am-active"</c:if>><a href="#">1</a></li>--%>
    <%--<li <c:if test="${pageInfo.currentPage == 2}">class="am-active"</c:if>><a href="#">2</a></li>--%>
    <%--<li <c:if test="${pageInfo.currentPage == 3}">class="am-active"</c:if>><a href="#">3</a></li>--%>
    <%--<li <c:if test="${pageInfo.currentPage == 4}">class="am-active"</c:if>><a href="#">4</a></li>--%>
    <%--<li <c:if test="${pageInfo.currentPage == 5}">class="am-active"</c:if>><a href="#">5</a></li>--%>

    <%--<li><span>...</span></li>--%>
    <%--<li><a class="next" href="#" onclick="pageSub('down')">&raquo;</a></li>--%>


  <li>
    共 ${pageInfo.totalCount} 条记录，
    每页<input type="text" id="rows_txt" size="1" value="${pageInfo.countOfCurrentPage}" />条，
    <button type="button" id="searchBtn" class="am-btn am-btn-primary btn-loading-example"
            data-am-loading="{spinner: 'circle-o-notch', loadingText: '刷新中...', resetText: '<span class=am-icon-refresh></span> 刷新'}"
            onclick="app.searchFormPage($('#pageForm'), $('#pageForm').attr('action'), this)"><span class="am-icon-refresh"></span> 刷新</button>
    共${pageInfo.totalPage}页
  </li>
</ul>
<script>
  $("select").selected();
  var obj = $("#page");
  var currentPage = Number("${pageInfo.currentPage}");
  var totalPage = Number("${pageInfo.totalPage}");
  $(function(){
    $("#rows_txt").keyup(function(){
      var rowsVal = $("#rows_txt").val();
      if(isNaN(rowsVal) || 1 > rowsVal) {
        $("#rows").val(rowsVal.substring(0, rowsVal.length-1));
      }
    });


    var liPrev;
    var liNext;
    if(currentPage == 1){
      liPrev = $("<li class=\"am-disabled\"><a class=\"prev\" href=\"#\">&laquo;</a></li>");
    }else{
      liPrev = $("<li><a class=\"prev\" href=\"#\" onclick=\"pageSub('up')\">&laquo;</a></li>");
    }
    obj.append(liPrev);
    if(totalPage <= 5){
      for(var i=1; i<=totalPage; i++){
        var li;
        if(currentPage == i){
          li = $("<li nam=\"pageNum\" class=\"am-active\"><a href=\"#\" onclick=\"pageSub('change', this)\">"+i+"</a></li>");
        }else{
          li = $("<li nam=\"pageNum\"><a href=\"#\" onclick=\"pageSub('change', this)\">"+i+"</a></li>");
        }
        obj.append(li);
      }
    }else{
      var baseNum = parseInt((currentPage-1)/5)*5;
      for(var i=1+baseNum; i<=(5+baseNum < totalPage ? 5+baseNum : totalPage); i++){
        var li;
        if(currentPage == i){
          li = $("<li class=\"am-active\"><a href=\"#\" onclick=\"pageSub('change', this)\">"+i+"</a></li>");
        }else{
          li = $("<li><a href=\"#\" onclick=\"pageSub('change', this)\">"+i+"</a></li>");
        }
        obj.append(li);
      }
    }

    if(currentPage == totalPage){
      liNext = $("<li class=\"am-disabled\"><a class=\"next\" href=\"#\">&raquo;</a></li>");
    }else{
      liNext = $("<li><a class=\"next\" href=\"#\" onclick=\"pageSub('down')\">&raquo;</a></li>");
    }
    obj.append(liNext);
  });

  function pageSub(flag, obj){
    var isSub = true;
    if(flag == "start"){
      $("#currentPage").val(1);
    }
    if(flag == "up"){
      if(currentPage > 1){
        $("#currentPage").val(currentPage-1);
      }else{
        isSub = false;
        alert("已经是第一页");
      }
    }
    if(flag == "down"){
      if(currentPage < totalPage){
        currentPage = parseInt(currentPage);
        $("#currentPage").val(currentPage+1);
      }else{
        isSub = false;
        alert("已经是最后一页");
      }
    }
    if(flag == "end"){
      $("#currentPage").val(totalPage);
    }
    if(flag == "change"){
      if(typeof (obj) == "undefined"){
        if($("#changePage").val() > 0 && $("#changePage").val() <= totalPage) {
          $("#currentPage").val($("#changePage").val());
        }else{
          isSub = false;
          alert("跳转的页数不正确");
        }
      }else{
        $("#currentPage").val($(obj).html());
      }
    }
    if(isSub){
      $("#rows").val($("#rows_txt").val());
      var url = $("#pageForm").attr("action");
      app.searchFormPage($('#pageForm'), url);
    }
  }
</script>