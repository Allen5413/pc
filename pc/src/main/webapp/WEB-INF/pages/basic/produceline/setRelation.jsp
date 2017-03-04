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
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:15%; height: 480px; float: left; margin-left: 5px;">
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

  <div class="am-panel am-panel-primary no-margin-bottom" style="width:53%; height: 480px; float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">关联的产品信息</div>
    <div class="am-in">
      <div class="table-head">
        <table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tr class="am-primary" style="border-right: 0px;">
            <th style="width: 20%;">名称</th>
            <th style="width: 10%;">类型</th>
            <th style="width: 15%;">工作模式</th>
            <th style="width: 18%;">产能(小时)</th>
            <th style="width: 15%;">合格率(%)</th>
            <th>最小批量(个)</th>
          </tr>
        </table>
      </div>
      <div class="table-body2" style="height: 402px; ">
        <form id="changeProductForm" name="changeProductForm" method="post">
          <input type="hidden" id="plId" name="plId" />
          <input type="hidden" id="wcId" name="wcId" />
          <table id="withProductTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
          </table>
        </form>
      </div>
    </div>
  </div>
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:30%; height: 480px; float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">产品信息查询</div>
    <div class="am-in">
      <div style="background-color: #fFF;width: 100%;padding: 0.7rem;">
        <form id="findProductForm" name="findProductForm">
          <label>编码：</label>
          <input type="text" name="code" style="width: 100px;" />&nbsp;&nbsp;&nbsp;&nbsp;
          <label >名称：</label>
          <input type="text" name="name" style="width: 100px;" /><br /><br />
          <label >类别：</label>
          <select id="type" name="type" data-am-selected="{btnWidth:'126px'}" onchange="app.changeSelect(this)">
            <option value=""></option>
            <option value="null">全部</option>
            <c:forEach items="${productTypeList}" var="productType">
              <option value="${productType.FCATEGORYID}">${productType.FNAME}</option>
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
          <th style="width:66%; ">名称</th>
          <th style="width: 17%;">类型</th>
          <th style="width: 17%;">自制件</th>
        </tr>
      </table>
    </div>
    <div class="table-body" STYLE="height: 312px;">
      <table id="findProductTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
      </table>
    </div>
  </div>
</div>
<script>
  $("select").selected();

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
                var tr = $("<tr><input type='hidden' name='pIds' value='"+product.id+"'></tr>");
                var td = $("<td style='width: 20%;' onclick='delWithProduct(this, " + product.plcpId + ")'>[" + product.FNUMBER + "]"+product.FNAME+"</td>");
                var td2 = $("<td style='width: 10%;' onclick='delWithProduct(this, " + product.plcpId + ")'>" + product.cateGoryName + "</td>");
                var td4 = $("<td style='width: 15%;'></td>");
                var td4Html = "<select id=\"wmId"+i+"\" name=\"wmIds\" data-am-selected=\"{btnWidth:'70px'}\" onchange=\"app.changeSelect(this)\">";
                td4Html += "<option value=''></option>";
                td4Html += "<option value='null'>全部</option>";
                <c:forEach items="${workModeList}" var="workMode">
                  td4Html += "<option value='${workMode.id}'>${workMode.name}</option>";
                </c:forEach>
                td4Html += "</select>";
                $(td4).append(td4Html);
                var td5 = $("<td style='width: 18%;'><input type='number' name='unitTimeCapacitys' style='width: 60px;' value='"+product.unitTimeCapacity+"' /></td>");
                var td6 = $("<td style='width: 15%;'><input type='number' name='qualifiedRates' style='width: 60px;' value='"+product.qualifiedRate+"' /></td>");
                var td7 = $("<td><input type='number' name='minBatchs' style='width: 60px;' value='"+product.minBatch+"' /></td>");
                $(tr).append(td).append(td2).append(td4).append(td5).append(td6).append(td7);
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
                var tr = $("<tr onclick='changeProduct(this, "+product.FMATERIALID+")'></tr>");
                var td = $("<td style='width: 66%;'>["+product.FNUMBER+"]"+product.FNAME+"</td>");
                var td2 = $("<td style='width: 17%;'>"+product.cateGoryName+"</td>");
                var td3 = $("<td style='width: 17%;'>"+(product.FERPCLSID == 1 ? "是" : "否")+"</td>");
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

    var table = $("#withProductTable");
    if($(table).find("tr:first").html().indexOf("没有关联产品信息") > -1){
      $(table).find("tr:first").remove();
    }

    var nameTd = $(trObj).find("td:first");
    var typeTd = $(nameTd).next();

    var tr = $("<tr><input type='hidden' name='pIds' value='"+pId+"'></tr>");
    var td = $("<td style='width: 20%;' onclick='delWithProduct(this)'>" + $(nameTd).html() + "</td>");
    var td2 = $("<td style='width: 10%;' onclick='delWithProduct(this)'>" + $(typeTd).html() + "</td>");
    var td4 = $("<td style='width: 15%;'></td>");
    var td4Html = "<select id=\"wmId"+($(table).find("tr").length)+"\" name=\"wmIds\" data-am-selected=\"{btnWidth:'70px'}\" onchange=\"app.changeSelect(this);\">";
    td4Html += "<option value=''></option>";
    td4Html += "<option value='null'>全部</option>";
    <c:forEach items="${workModeList}" var="workMode">
    td4Html += "<option value='${workMode.id}''>${workMode.name}</option>";
    </c:forEach>
    td4Html += "</select>";
    $(td4).append(td4Html);
    var td5 = $("<td style='width: 18%;'><input type='number' name='unitTimeCapacitys' style='width: 60px;' /></td>");
    var td6 = $("<td style='width: 15%;'><input type='number' name='qualifiedRates' style='width: 60px;' /></td>");
    var td7 = $("<td><input type='number' name='minBatchs' style='width: 60px;' /></td>");
    $(tr).append(td).append(td2).append(td4).append(td5).append(td6).append(td7);
    table.append(tr);
    $("select").selected();

  }

  function delWithProduct(trObj){
    $(trObj).parent().remove();
  }
</script>