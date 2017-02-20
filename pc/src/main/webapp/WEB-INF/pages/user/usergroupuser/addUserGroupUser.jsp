<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form" style="margin-top: 10px;" id="addUserGroupUserForm" name="addUserGroupUserForm" method="post">
  <input type="hidden" name="userId" value="${userId}"/>
  <c:forEach var="userGroup" items="${userGroups}">
    <div class="am-u-sm-4" style="float:left;">
        <div class="am-form-group">
            <label class="am-checkbox-inline">
                <input name="userGroupCheck" id="UserGroupId_${userGroup.id}" data-am-ucheck
                       type="checkbox" value="${userGroup.id}"/>${userGroup.name}
            </label>
        </div>
    </div>
  </c:forEach>

</form>
<script type="application/javascript">
  $(function(){
      var defaultCheckId = '${currentGroups}';
      $.each(JSON.parse(defaultCheckId),function(i,val){
          $('#UserGroupId_'+val.userGroupId).attr("checked","checked");
      });
      $("input[type='checkbox']").uCheck();
  });
</script>