<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html class="no-js">
<head>
  <%@ include file="common/meta.jsp"%>
  <%@ include file="common/taglibs.jsp"%>
</head>
<script type="text/javascript">
  $(function(){
    //回车事件
    document.onkeydown = function(e){
      var ev = document.all ? window.event : e;
      if(ev.keyCode==13) {
        sub();
      }
    }
  });

  function sub(){
    var loginName = $.trim($("#loginName").val());
    var pwd = $.trim($("#pwd").val());
    if(loginName == ""){
      $("#loginName").css("color", "red");
    }
    else if(pwd == ""){
      $("#pwd").css("color", "red");
    }else{
      var params = {
        "loginName":loginName,
        "pwd":pwd
      };
      $.ajax({
        url:"${pageContext.request.contextPath}/login.json",
        method : 'POST',
        async:false,
        data:params,
        success:function(data){
          if(data.state == "0"){
            location.href = "${pageContext.request.contextPath}/index.html";
          }else {
            $("#msg").html(data.msg);
          }
        }
      });
    }
  }
</script>
<body>
<div class="am-g myapp-login">
  <div class="myapp-login-bg">
    <div class="myapp-login-logo">
      <i class="am-icon-stumbleupon"></i>
    </div>
    <div class="am-u-sm-10 myapp-login-form">
      <form class="am-form">
        <fieldset>
          <center>
            <div class="am-form-group" style="color: #ffffff; text-align: center; font-size: 30px;">
              欢迎登录${name}系统管理后台
            </div>
            <div class="am-form-group">
              <input type="text" class="" id="loginName" name="loginName" placeholder="请输入用户名" style="width: 300px;" onfocus="$(this).css('color', '#fff');">
            </div>
            <div class="am-form-group">
              <input type="password" class="" id="pwd" name="pwd" placeholder="请输入密码" style="width: 300px;" onfocus="$(this).css('color', '#fff');">
            </div>
            <div class="am-form-group">
              <span id="msg" style="color: #f60; font-weight: bold; font-size: 18px;"></span>
            </div>
            <p><button type="button" id="loginBtn" class="am-btn am-btn-default" onclick="sub()" style="width: 300px;">登&nbsp;&nbsp;录</button></p>
          </center>
        </fieldset>
      </form>
    </div>
  </div>
</div>
</body>
</html>