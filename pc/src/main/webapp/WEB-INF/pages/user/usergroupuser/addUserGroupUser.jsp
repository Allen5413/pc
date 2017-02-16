<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="am-form am-form-horizontal" style="margin-top: 10px;" id="addUserGroupUserForm" name="addUserGroupUserForm" method="post">
  <input type="hidden" name="userId" value="${userId}"/>
  <c:forEach var="userGroup" items="${userGroups}">
    <div class="am-u-sm-3">
        <input class="am-input-sm" name="userGroupCheck" id="UserGroupId_${userGroup.id}" type="checkbox" value="${userGroup.id}"/>${userGroup.name}
    </div>
  </c:forEach>
</form>
<script type="application/javascript">
  $(function(){
      var defaultCheckId = '${currentGroups}';
      $.each(JSON.parse(defaultCheckId),function(i,val){
          $('#UserGroupId_'+val.userGroupId).attr("checked","checked");
      });
  });
</script>