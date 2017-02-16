<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="editForm" name="editForm" method="post" data-am-validator>
  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >旧密码：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="password" placeholder="输入旧密码" required id="oldPwd" name="oldPwd" />
    </div>
    <div class="am-u-sm-5">*必填</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >新密码：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="password" placeholder="输入新密码" required id="newPwd" name="newPwd" />
    </div>
    <div class="am-u-sm-5">*必填</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >确认新密码：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="password" placeholder="输入确认新密码" min="0" required id="newPwdAgain" name="newPwdAgain" />
    </div>
    <div class="am-u-sm-5">*必填，必须与新密码一致</div>
  </div>
</form>