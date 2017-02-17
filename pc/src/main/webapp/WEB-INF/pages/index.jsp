<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html class="no-js">
<head>
  <%@ include file="common/meta.jsp"%>
  <%@ include file="common/taglibs.jsp"%>
</head>
<script type="text/javascript">
  function openEditPwd(){
    app.openDialog('${pageContext.request.contextPath}/editPwd/open.html', '修改密码', 500, 300, function(index){
      var oldPwd = $("#oldPwd").val();
      var newPwd = $("#newPwd").val();
      var newPwdAgain = $("#newPwdAgain").val();
      if(oldPwd == ""){
        app.msg("请输入旧密码", 1);
        return;
      }
      if(newPwd == ""){
        app.msg("请输入新密码", 1);
        return;
      }
      if(newPwd != newPwdAgain){
        app.msg("新密码不一致", 1);
        return;
      }
      app.edit("${pageContext.request.contextPath}/editPwd/editor.json", $('#editForm').serialize(), index);
    });
  }
</script>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong style="font-size: 20px;">Allen管理系统</strong>
  </div>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          <span class="am-icon-users"></span> ${name} 管理员 <span class="am-icon-caret-down"></span>
        </a>
        <ul class="am-dropdown-content">
          <li><a href="#" onclick="openEditPwd()"><span class="am-icon-cog"></span> 修改密码</a></li>
          <li><a href="${pageContext.request.contextPath}/logout.html"><span class="am-icon-power-off"></span> 退出</a></li>
        </ul>
      </li>
    </ul>
  </div>
</header>

<div class="am-cf admin-main">
  <!-- sidebar start -->
  <div class="admin-sidebar" style="width: 200px;">
    <ul class="am-list admin-sidebar-list">
      <c:forEach var="menu" items="${menu}" varStatus="status">
        <li class="admin-parent" style="background-color:#19a7f0">
          <a class="am-cf" data-am-collapse="{target: '#menu_${status.index}'}" style="color:#fff;" onClick="app.clickMenu(${status.index})">
            <span class="am-icon-file"></span> ${menu.key} <span id="${status.index}" class="am-icon-angle-right am-fr am-margin-right"></span>
          </a>
          <ul class="am-list am-collapse admin-sidebar-sub" id="menu_${status.index}" style="padding-left:0px;">
            <c:forEach var="resource" items="${menu.value}" varStatus="status2">
              <c:if test="${resource.isButton==1}">
                  <li style="background-color:#f9f9f9">
                    <a href="#" style="color:#19a7f0;" onclick="app.addTab('${pageContext.request.contextPath}${resource.url}?resourceId=${resource.id}', '${resource.name}', ${status.index}, ${status2.index}, 0)"><span class="am-icon-table" style="padding-left:14px;"></span> ${resource.name}</a>
                  </li>
              </c:if>
            </c:forEach>
          </ul>
        </li>
      </c:forEach>
    </ul>
  </div>
  <!-- sidebar end -->



  <div class="admin-content">
    <div class="am-tabs" data-am-tabs>

      <ul id="tab" class="am-tabs-nav am-nav am-nav-tabs">
        <li id="homePageTab" class="am-active" lang="0"><a lang="${pageContext.request.contextPath}/index/main.htm" href="#contentPage" onclick='app.clickTab(this)' style="float: left">首页</a></li>
      </ul>
      <span class="am-icon-remove" style="margin-top:-26px; float:right"> <a href="#" onclick="app.removeTab()">关闭</a></span>

      <div class="am-tabs-bd" style="height:100%; overflow-y:auto;">
        <div class="am-tab-panel am-fade am-in am-active" id="contentPage">
          欢迎使用Allen的后台管理系统
        </div>
      </div>
      <footer data-am-widget="footer" class="am-footer am-footer-default no-padding" style="padding: 0 !important;" data-am-footer="{  }">
        <div class="am-footer-miscs ">
          <p class="browsehappy">如果你样式有问题，可能是你正在使用<strong>过时</strong>的浏览器，UI 不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
            以获得更好的体验！</p>
        </div>
      </footer>
    </div>
  </div>
</div>
</body>
<script type="application/javascript">
  $(function(){
      $('.am-tabs-bd').height($(window).height()-$('.am-footer').height()-$('.admin-header').height()-35);
  });
</script>
</html>