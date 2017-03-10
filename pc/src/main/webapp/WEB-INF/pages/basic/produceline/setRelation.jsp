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

  <div class="am-panel am-panel-primary no-margin-bottom" style="width:45%; height: 480px; float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">关联的产品信息</div>
    <div class="am-in">
      <div class="table-head">
        <table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tr>
            <td colspan="99">
              <button class="am-btn am-btn-primary am-btn-sm" type="button" onClick="setCoreProduct()"><span class="am-icon-cog"></span> 关联产品</button>
            </td>
          </tr>
          <tr class="am-primary" style="border-right: 0px;">
            <th style="width: 50%;">名称</th>
            <th style="width: 15%;">类型</th>
            <th style="width: 15%;">合格率(%)</th>
            <th>操作</th>
          </tr>
        </table>
      </div>
      <div class="table-body" style="height: 350px; ">
        <form id="changeProductForm" name="changeProductForm" method="post">
          <input type="hidden" id="plId" name="plId" />
          <input type="hidden" id="wcId" name="wcId" />
          <table id="withProductTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
          </table>
        </form>
      </div>
    </div>
  </div>
  <div class="am-panel am-panel-primary no-margin-bottom" style="width:38%; height: 480px; float: left; margin-left: 5px;">
    <div class="am-panel-hd am-cf">关联的班组信息</div>
    <div class="am-in">
      <div class="table-head">
        <table class="am-table am-table-bordered am-table-striped am-table-hover no-margin-bottom" style="width:100%;">
          <tr>
            <td colspan="99">
              <button class="am-btn am-btn-primary am-btn-sm" type="button" onclick="setProductCg()"><span class="am-icon-cog"></span> 关联班组</button>
            </td>
          </tr>
          <tr class="am-primary" style="border-right: 0px;">
            <th style="width:8%;">序号</th>
            <th style="width:20%;">名称</th>
            <th style="width:20%;">工作模式</th>
            <th style="width:20%;">产能（小时）</th>
            <th style="width:15%;">最小批量</th>
            <th>操作</th>
          </tr>
        </table>
      </div>
      <div class="table-body" STYLE="height: 350px;">
        <table id="findProductCgTable" class="am-table am-table-bordered am-table-striped am-table-hover" style="width:100%;">
        </table>
      </div>
    </div>
  </div>
</div>
<script>
  $("select").selected();

  var pcId = 0;
  var plcpIdForAddCg = 0;

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
      findProduct(treeNode.pId, treeNode.id);
    }
  }

  function findProduct(plId, wcId){
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/findProductByPlIdAndWcId/find.json",
      data:{"plId":plId, "wcId":wcId},
      async: false,
      success: function(data) {
        if(data.state == 0){
          var table = $("#withProductTable");
          $(table).html("");
          var productList = data.productList;
          if(0 < productList.length) {
            for(var i=0; i<productList.length; i++) {
              var product = productList[i];
              var tr = $("<tr onclick='findCG("+product.plcpId+", this)'><input type='hidden' name='pIds' value='"+product.id+"'></tr>");
              var td = $("<td style='width: 50%;' >[" + product.FNUMBER + "]"+product.FNAME+"</td>");
              var td2 = $("<td style='width: 15%;' >" + product.cateGoryName + "</td>");
              var td3 = $("<td style='width: 15%;' >"+product.qualifiedRate+"</td>");
              var td4 = $("<td><a class=\"am-badge am-badge-danger am-radius am-text-lg\" onclick=\"delWithProduct(this, "+product.plcpId+")\"><span class=\"am-icon-trash-o\"></span> 删除</a></td>");
              $(tr).append(td).append(td2).append(td3).append(td4);
              table.append(tr);
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

  function setProduceLineCore(){
    var selectedNodes = zTreeObj.getSelectedNodes();
    if(1 > selectedNodes.length){
      app.msg("请先选择一个生产线", 1);
      return;
    }else{
      var isParent = selectedNodes[0].isParent;
      if(isParent){
        var plId = selectedNodes[0].id;
        app.openDialog('${pageContext.request.contextPath}/addProduceLineCore/open.html?plId='+plId, '关联工作中心', 800, 500, function(index){
          var flag = true;
          var msg = "";
          var wcIdObj = {};
          var snoObj = {};
          $("[name=wcIds]").each(function(){
            var wcId = $(this).val();
            if(wcIdObj[wcId]){
              msg += "工作中心不能重复选择！<br />";
              flag = false;
            }else{
              wcIdObj[wcId] = true;
            }
          });
          $("[name=snos]").each(function(){
            var sno = $(this).val();
            if(!vaild.vaildInteger(sno, 1)){
              msg += "请输入一个大于0的正确的序号！<br />";
              flag = false;
            }
            if(snoObj[sno]){
              msg += "序号不能重复选择！<br />";
              flag = false;
            }else{
              snoObj[sno] = true;
            }
          });
          if(!flag){
            app.msg(msg, 1);
            return;
          }
          app.add("${pageContext.request.contextPath}/addProduceLineCore/add.json", $('#setCoreForm').serialize(), index, function(){
            zTreeObj.reAsyncChildNodes(selectedNodes[0], "refresh");
          });
        });
      }else{
        app.msg("请选择一个生产线", 1);
        return;
      }
    }
  }

  function setCoreProduct(){
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
    var plId = selectedNodes[0].plId;
    var wcId = selectedNodes[0].id;
    app.openDialog('${pageContext.request.contextPath}/addProduceLineCoreProduct/open.html?plId='+plId+'&wcId='+wcId, '关联产品', 800, 500, function(index){
      if(0 < $("#delPlcpIds").val().length) {
        $("#delPlcpIds").val($("#delPlcpIds").val().substring(0, $("#delPlcpIds").val().length - 1));
      }

      var flag = true;
      var msg = "";
      var pIdObj = {};
      $("[name=pIdsForAddP]").each(function(){
        var pId = $(this).val();
        if(pIdObj[pId]){
          msg += "产品不能重复选择！<br />";
          flag = false;
        }else{
          pIdObj[pId] = true;
        }
      });
      $("[name=qualifiedRates]").each(function(){
        var qualifiedRate = $(this).val();
        if(!vaild.vaildNumber(qualifiedRate, 0, 100)){
          msg += "请输入一个0~100的正确的合格率！<br />";
          flag = false;
        }
      });
      if(!flag){
        app.msg(msg, 1);
        return;
      }

      app.add("${pageContext.request.contextPath}/addProduceLineCoreProduct/add.json", $('#setCoreProductForm').serialize(), index, function(){
        findProduct(plId, wcId);
      });
    });
  }

  function delWithProduct(trObj, plcpId){
    app.confirm("您确定要删除该产品？", function(){
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/delProduceLineCoreProduct/del.json",
        data:{"id":plcpId},
        async: false,
        success: function(data) {
          app.msg("删除成功", 0);
          $(trObj).parent().parent().remove();
        }
      });
    });
  }

  function findCG(plcpId, obj){
    this.plcpIdForAddCg = plcpId;
    if(typeof (obj) != "undefined") {
      $('#withProductTable tr').removeClass('am-active');
      $(obj).addClass('am-active');
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"${pageContext.request.contextPath}/findPlcpcgByPlcpId/find.json",
      data:{"plcpId":plcpId},
      async: false,
      success: function(data) {
        if(data.state == 0){
          var table = $("#findProductCgTable");
          $(table).html("");
          var cgList = data.list;
          if(0 < cgList.length) {
            for(var i=0; i<cgList.length; i++) {
              var cg = cgList[i];
              var tr = $("<tr><input type='hidden' name='plcpcgIds' value='"+cg.id+"'></tr>");
              var td = $("<td style='width: 8%;'>"+cg.sno+"</td>");
              var td2 = $("<td style='width: 20%;'>"+cg.cgName+"</td>");
              var td3 = $("<td style='width: 20%;'>"+cg.wmName+"</td>");
              var td4 = $("<td style='width: 20%;'>"+(cg.unit_time_capacity/3600).toFixed(2)+"</td>");
              var td5 = $("<td style='width: 15%;'>"+cg.min_batch+"</td>");
              var td6 = $("<td><a class=\"am-badge am-badge-danger am-radius am-text-lg\" onclick=\"delWithProductCg(this, "+cg.id+")\"><span class=\"am-icon-trash-o\"></span> 删除</a></td>");
              $(tr).append(td).append(td2).append(td3).append(td4).append(td5).append(td6);
              table.append(tr);
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


  function setProductCg(){
    if(1 > this.plcpIdForAddCg){
      app.msg("请先选择一个生产线下的工作中心下的一个产品", 1);
      return;
    }
    app.openDialog('${pageContext.request.contextPath}/addPlcpcg/open.html?plcpId='+this.plcpIdForAddCg, '关联班组', 800, 500, function(index){
      if(0 < $("#delPlcpcgIds").val().length) {
        $("#delPlcpcgIds").val($("#delPlcpcgIds").val().substring(0, $("#delPlcpcgIds").val().length - 1));
      }

      var flag = true;
      var msg = "";
      var cgIdsObj = {};
      var snoObj = {};
      $("[name=cgIds]").each(function(){
        var cgId = $(this).val();
        if(cgIdsObj[cgId]){
          msg += "班组不能重复选择！<br />";
          flag = false;
        }else{
          cgIdsObj[cgId] = true;
        }
      });
      $("[name=snos]").each(function(){
        var sno = $(this).val();
        if(!vaild.vaildInteger(sno, 1)){
          msg += "请输入一个大于0的正确的序号！<br />";
          flag = false;
        }
        if(snoObj[sno]){
          msg += "序号不能重复选择！<br />";
          flag = false;
        }else{
          snoObj[sno] = true;
        }
      });
      $("[name=unitTimeCapacitys]").each(function(){
        var unitTimeCapacity = $(this).val();
        if(!vaild.vaildNumber(unitTimeCapacity, 0)){
          msg += "请输入一个大于0的正确的产能！<br />";
          flag = false;
        }
      });
      $("[name=minBatchs]").each(function(){
        var minBatch = $(this).val();
        if(!vaild.vaildInteger(minBatch, 1)){
          msg += "请输入一个大于0的正确的最小批量！<br />";
          flag = false;
        }
      });

      if(!flag){
        app.msg(msg, 1);
        return;
      }
      app.add("${pageContext.request.contextPath}/addPlcpcg/add.json", $('#setProductCgForm').serialize(), index, function(){
        findCG(this.plcpIdForAddCg);
      });
    });
  }

  function delWithProductCg(trObj, plcpcgId){
    app.confirm("您确定要删除该班组？", function(){
      $.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/delPlcpcg/del.json",
        data:{"id":plcpcgId},
        async: false,
        success: function(data) {
          app.msg("删除成功", 0);
          $(trObj).parent().parent().remove();
        }
      });
    });
  }
</script>