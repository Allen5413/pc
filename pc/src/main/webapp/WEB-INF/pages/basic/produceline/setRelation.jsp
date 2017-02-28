<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/permission.tld" %>
<style type="text/css">
  .table-head{padding-right:17px;}
  .table-body, .table-body2{width:100%;overflow-y:scroll;}
  .table-head table,.table-body, .table-body2 table{width:100%;}
</style>
<div id="setRelationDiv">
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:15%; float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">生产线信息</div>
    <div id="notWith" class="am-in">
        <div style="background-color: #fFF;width: 100%;padding: 0.7rem;border-bottom: solid 1px #ddd;">
          <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="setProduceLineCore()"><span class="am-icon-cog"></span> 关联生产中心</button>
        </div>
      <div id="zTreeDiv" style="overflow: auto;">
        <ul id="plTree" class="ztree"></ul>
      </div>
    </div>
  </div>

  <div class="am-panel am-panel-primary no-margin-bottom" style="width:53%;float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">关联的产品信息</div>
    <div class="am-in">
      <div class="table-head">
        <table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tr class="am-primary" style="border-right: 0px;">
            <th style="width: 20%;">名称</th>
            <th style="width: 10%;">产品类型</th>
            <th style="width: 10%;">自制件</th>
            <th style="width: 15%;">工作模式</th>
            <th style="width: 15%;">单位时间产能</th>
            <th style="width: 10%;">合格率</th>
            <th>最小批量</th>
          </tr>
        </table>
      </div>
      <div class="table-body2">
        <table id="withProductTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
        </table>
      </div>
    </div>
  </div>
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:30%;float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">产品信息查询</div>
    <div class="am-in">
      <div style="background-color: #fFF;width: 100%;padding: 0.7rem;">
        <form id="findProductForm" name="findProductForm">
          <label>编码：</label>
          <input type="text" name="code" style="width: 100px;" />&nbsp;&nbsp;&nbsp;&nbsp;
          <label >名称：</label>
          <input type="text" name="name" style="width: 100px;" /><br /><br />
          <label >类别：</label>
          <select id="type" name="type" onchange="app.changeSelect(this)">
            <option value=""></option>
            <option value="null">全部</option>
            <c:forEach items="${productTypeList}" var="productType">
              <option value="${productType.id}">${productType.name}</option>
            </c:forEach>
          </select>&nbsp;&nbsp;&nbsp;&nbsp;
          <button type="button" id="searchProductBtn" class="am-btn am-btn-primary btn-loading-example"
                  data-am-loading="{spinner: 'circle-o-notch', loadingText: '查询中...', resetText: '<span class=am-icon-search></span> 查询'}"
                  onclick="searchProduct()"><span class="am-icon-search"></span> 查询</button>
        </form>
      </div>
    </div>
    <div class="table-head">
      <table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
        <tr class="am-primary" style="border-right: 0px;">
          <th>名称</th>
          <th style="width: 17%;">产品类型</th>
          <th style="width: 20%;">自制件</th>
        </tr>
      </table>
    </div>
    <div class="table-body">
      <table id="findProductTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
      </table>
    </div>
  </div>
</div>
<script>
  $("select").selected();

  $('#setRelationDiv .table-body').height(433);
  $('#setRelationDiv .table-body2').height(521);
  $('#zTreeDiv').height(515);

  var setting = {
    data: {
      simpleData: {
        enable: true
      }
    },
    async: {
      enable: true,
      contentType:"application/x-www-form-urlencoded",
      type:"post",
      url: "${pageContext.request.contextPath}/findWorkCoreByPlId/find.json",
      autoParam: ["id=plId"]
    },
    callback: {
      onClick: zTreeOnClick
    }
  };
  var zNodes = [
    <c:forEach items="${produceLineList}" var="pl" varStatus="status">
      <c:if test="${status.index > 0}">, </c:if>
      {id:${pl.id}, pId:0, name: "[${pl.code}]${pl.name}", isParent:true}
    </c:forEach>
  ];
  var zTreeObj = $.fn.zTree.init($("#plTree"), setting, zNodes);

  function zTreeOnClick(event, treeId, treeNode) {
    if(null != treeNode.pId){
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/findProductByPlIdAndWcId/find.json",
        data:{"plId":treeNode.pId, "wcId":treeNode.id},
        async: false,
        success: function(data) {
          if(data.state == 0){

            var table = $("#withProductTable");
            $(table).html("");

            var productList = data.productList;
            if(0 < productList.length) {
              for(var i=0; i<productList.length; i++) {
                var product = productList[i];
                var tr = $("<tr><input type='hidden' name='' </tr>");
                var td = $("<td style='width: 20%;' onclick='delWithProduct(this, " + product.plcpId + ")'>[" + product.code + "]"+product.name+"</td>");
                var td2 = $("<td style='width: 10%;' onclick='delWithProduct(this, " + product.plcpId + ")'>" + product.tName + "</td>");
                var td3 = $("<td style='width: 10%;' onclick='delWithProduct(this, " + product.plcpId + ")'>" + (product.selfMade == 0 ? "否":"是") + "</td>");
                var td4 = $("<td style='width: 15%;'></td>");
                var td4Html = "<select id=\"wmId"+i+"\" data-am-selected=\"{btnWidth:'70px'}\" onchange=\"app.changeSelect(this); changeWM(this, "+product.plcpId+");\">";
                td4Html += "<option value=''></option>";
                td4Html += "<option value='null'>全部</option>";
                <c:forEach items="${workModeList}" var="workMode">
                  td4Html += "<option value='${workMode.id}'>${workMode.name}</option>";
                </c:forEach>
                td4Html += "</select>";
                $(td4).append(td4Html);
                var td5 = $("<td style='width: 15%;'><input name='unitTimeCapacitys' style='width: 60px;' /></td>");
                var td6 = $("<td style='width: 10%;'><input name='qualifiedRates' style='width: 60px;' /></td>");
                var td7 = $("<td><input name='minBatchs' style='width: 60px;' /></td>");
                $(tr).append(td).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
                table.append(tr);
                $("select").selected();
                var op = $("#wmId"+i).find("option[value='"+product.wmId+"']");
                $(op).attr('selected', true);
              }
            }else{
              var tr = $("<tr></tr>");
              var td = $("<td style='color: #ff0000;' align='center'>没有关联产品信息</td>");
              $(tr).append(td);
              table.append(tr);
            }
          }else{
            app.msg(data.msg, 1);
          }
        }
      });
    }
  };

  function setProduceLineCore(){
    var selectedNodes = zTreeObj.getSelectedNodes();
    if(1 > selectedNodes.length){
      app.msg("请先选择一个生产线", 1);
      return;
    }else{
      var isParent = selectedNodes[0].isParent;
      if(isParent){
        var plId = selectedNodes[0].id;
        app.openDialog('${pageContext.request.contextPath}/setProduceLineCoreForPlId/open.html?plId='+plId, '关联工作中心', 800, 700, function(index){
          app.add("${pageContext.request.contextPath}/setProduceLineCoreForPlId/set.json", $('#setForm').serialize(), index, function(){
            zTreeObj.reAsyncChildNodes(selectedNodes[0], "refresh");
          });
        });
      }else{
        app.msg("请选择一个生产线", 1);
        return;
      }
    }
  }

  function searchProduct(){
    $("#searchProductBtn").button('loading');
    setTimeout(function(){
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/findProductList/find.json",
        data:$("#findProductForm").serialize(),
        async: false,
        success: function(data) {
          $("#searchProductBtn").button('reset');
          if(data.state == 0){
            var productList = data.productList;
            var table = $("#findProductTable");
            table.find("tr").remove();
            if(0 < productList.length){
              for(var i=0; i<productList.length; i++){
                var product =  productList[i];
                var tr = $("<tr onclick='changeProduct(this, "+product.id+")'></tr>");
                var td = $("<td>["+product.code+"]"+product.name+"</td>");
                var td2 = $("<td style='width: 17%;'>"+product.tName+"</td>");
                var td3 = $("<td style='width: 20%;'>"+(product.self_made == 0 ? "否" : "是")+"</td>");
                tr.append(td).append(td2).append(td3);
                table.append(tr);
              }
            }else{
              var tr = $("<tr></tr>");
              var td = $("<td colspan='999' align='center' style='color: #ff0000;'>没有找到相关数据</td>");
              tr.append(td);
              table.append(tr);
            }
          }else{
            app.msg(data.msg, 1);
          }
        }
      });
    }, 100);
  }

  function changeProduct(trObj, pId){
    var selectedNodes = zTreeObj.getSelectedNodes();
    if(1 > selectedNodes.length){
      app.msg("请先选择一个生产线下的工作中心", 1);
      return;
    }
    var isParent = selectedNodes[0].isParent;
    if (isParent) {
      app.msg("请选择一个工作中心", 1);
      return;
    }

    var wcId = selectedNodes[0].id;
    var plId = selectedNodes[0].pId;

    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/addProduceLineCoreProduct/add.json",
      data:{"plId":plId, "wcId":wcId, "pId":pId},
      async: false,
      success: function(data) {
        if(data.state == 0){
          var table = $("#withProductTable");
          if($(table).find("tr:first").html().indexOf("没有关联产品信息") > -1){
            $(table).find("tr:first").remove();
          }

          var nameTd = $(trObj).find("td:first");
          var typeTd = $(nameTd).next();
          var selfTd = $(typeTd).next();

          var tr = $("<tr></tr>");
          var td = $("<td style='width: 20%;' onclick='delWithProduct(this, "+data.plcpId+")'>" + $(nameTd).html() + "</td>");
          var td2 = $("<td style='width: 10%;' onclick='delWithProduct(this, "+data.plcpId+")'>" + $(typeTd).html() + "</td>");
          var td3 = $("<td style='width: 10%;' onclick='delWithProduct(this, "+data.plcpId+")'>" + $(selfTd).html() + "</td>");
          var td4 = $("<td style='width: 15%;'></td>");
          var td4Html = "<select id=\"wmId"+($(table).find("tr").length)+"\" name=\"wmId\" data-am-selected=\"{btnWidth:'70px'}\" onchange=\"app.changeSelect(this); changeWM(this, "+data.plcpId+");\">";
          td4Html += "<option value=''></option>";
          td4Html += "<option value='null'>全部</option>";
          <c:forEach items="${workModeList}" var="workMode">
          td4Html += "<option value='${workMode.id}''>${workMode.name}</option>";
          </c:forEach>
          td4Html += "</select>";
          $(td4).append(td4Html);
          var td5 = $("<td style='width: 15%;'><input name='unitTimeCapacitys' style='width: 60px;' /></td>");
          var td6 = $("<td style='width: 10%;'><input name='qualifiedRates' style='width: 60px;' /></td>");
          var td7 = $("<td><input name='minBatchs' style='width: 60px;' /></td>");
          $(tr).append(td).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
          table.append(tr);
          $("select").selected();
        }else{
          app.msg(data.msg, 1);
        }
      }
    });
  }

  function delWithProduct(trObj, plcpId){
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/delProduceLineCoreProduct/del.json",
      data:{"id":plcpId},
      async: false,
      success: function(data) {
        if(data.state == 0){
          $(trObj).parent().remove();
        }else{
          app.msg(data.msg, 1);
        }
      }
    });
  }

  function changeWM(obj, plcpId){
    var wmId = $(obj).val();
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/editProduceLineCoreProductForWmIdById/editor.json",
      data:{"id":plcpId, "wmId":wmId},
      async: false,
      success: function(data) {
        if(data.state == 0){
        }else{
          app.msg(data.msg, 1);
        }
      }
    });
  }
</script>