<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" id="addForm" name="addForm" method="post">
  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >编号：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入编号" required id="add_code" name="code"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >名称：</label></div>
    <div class="am-u-sm-4">
      <input class="am-input-sm" type="text" placeholder="输入名称" required id="add_name" name="name"  />
    </div>
    <div class="am-u-sm-5">*必填，不可重复</div>
  </div>

  <div class="am-g am-margin-top">
    <div class="am-u-sm-3 am-text-right"><label >是否公用：</label></div>
    <div class="am-u-sm-4">
      <label class="am-radio am-secondary" style="margin: -2px 0px 0px -20px;">
        <label class="am-radio-inline">
          <input type="radio" name="isPublic" value="1" data-am-ucheck checked> 是
        </label>
        <label class="am-radio-inline">
          <input type="radio" name="isPublic" value="0" data-am-ucheck> 否
        </label>
      </label>
    </div>
    <div class="am-u-sm-5"></div>
  </div>
</form>
<script>
  $(function() {
    $("input[type='checkbox'], input[type='radio']").uCheck();
  });
</script>