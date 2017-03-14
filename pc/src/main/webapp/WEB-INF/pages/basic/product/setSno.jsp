<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="eidtProductSnoForm" name="eidtProductSnoForm" method="post">
  <input type="hidden" id="FMATERIALID" name="FMATERIALID" value="${id}"/>
  <div class="am-form-group">
    <label class="am-u-sm-3 am-form-label no-padding-right" for="FSNO">顺序号</label>
    <div class="am-u-sm-4 no-padding-right">
      <input class="am-input-sm no-padding-right" value="${fsno}" type="number" min="0" placeholder="顺序号" required id="FSNO" name="FSNO"  />
    </div>
    <div class="am-u-sm-5" style="padding-top: 5px;">产品先后顺序</div>
  </div>
</form>
<script>
  $(function(){
        $('#FSNO').on('change',function(){
            if(isNaN($(this).val())){
                $(this).val(0);
            }else{
                $(this).val(Number($(this).val()).toFixed(0));
            }
        });
  });
</script>