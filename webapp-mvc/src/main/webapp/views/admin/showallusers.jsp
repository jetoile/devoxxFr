<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="center" id="allUsersContainer">
	<table class="jtable" id="allUsersTable">
		<tr>
			<th><spring:message code="admin.user.name" text="Username" /></th>
			<th><spring:message code="admin.user.mail" text="Email" /></th>
			<th><spring:message code="admin.label.action" text="Action" /></th>
		</tr>
		<c:forEach items="${allUserResponses}" var="userResponse">
			<tr>
				<td>${userResponse.name}</td>
				<td>${userResponse.mail}</td>
				<td>
				<a href='<c:url value="/admin/user/${userResponse.name}" />' class="ui-btn">
				<spring:message code="admin.btn.show" text="Show" /></a>
				&nbsp;
				<a href='<c:url value="/admin/user/${userResponse.name}/delete" />' class="ui-btn">
	            <spring:message code="admin.btn.delete" text="Remove" /></a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href='<c:url value="/admin/" />' class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
</div>