<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<script defer src="/js/jQuery-3.6.1.js"></script>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
	<h1><spring:message code="user.message.update_existing"/></h1>
	<form:form method="post" action="update_user" modelAttribute="userDto">
		<input type="hidden" />
		<input name="id" type="hidden" value="${requestScope.user.id}"/>
		<form:label for="first-name-input" path="firstName"><spring:message code="user.update_form.first_name"/> </form:label>
		<form:input id="first-name-input" path="firstName" type="text" minlength="1" required="required" value="${requestScope.user.firstName}"/> <form:errors path="firstName"/>
		<br/>
		<form:label for="last-name-input" path="lastName"><spring:message code="user.update_form.last_name"/> </form:label>
		<form:input id="last-name-input" path="lastName" type="text" minlength="1" required="required" value="${requestScope.user.lastName}"/> <form:errors path="lastName"/>
		<br/>
		<form:label for="email-input" path="email"><spring:message code="user.update_form.email"/> </form:label>
		<form:input id="email-input" path="email" type="email" required="required" value="${requestScope.user.email}"/> <form:errors path="email"/>
		<br/>
		<form:label for="password-input" path="password"><spring:message code="user.update_form.password"/> </form:label>
		<form:input id="password-input" path="password" type="password" minlength="4" required="required"/> <form:errors path="password"/>
		<br/>
		
			<form:label for="role-input-user" path="userRoleDto">
				<spring:message code="user.update_form.role.user"/>
			</form:label>
			<form:errors path="userRoleDto"/>
			<input id="role-input-user" name="userRoleDto" type="radio" value="USER" required="required"/>
			
			<form:label for="role-input-manager" path="userRoleDto">
				<spring:message code="user.update_form.role.manager"/>
			</form:label>
			<form:errors path="userRoleDto"/>
			<input id="role-input-manager" name="userRoleDto" type="radio" value="MANAGER" required="required"/>
			
			<form:label for="role-input-admin" path="userRoleDto">
				<spring:message code="user.update_form.role.admin"/>
			</form:label>
			<form:errors path="userRoleDto"/>
			<input id="role-input-admin" name="userRoleDto" type="radio" value="ADMIN" required="required"/>
			<br/>
		
		<input type ="submit" value="<spring:message code="user.update_form.button_update"/>"/>

	</form:form>

</body>
</html>