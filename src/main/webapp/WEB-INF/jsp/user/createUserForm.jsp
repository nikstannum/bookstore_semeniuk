<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
	<h1><spring:message code="user.message.register_new_user"/></h1>
	<div>
		<form:form method="post" action="create_user" modelAttribute="userDto">
			<input type="hidden" />
			<form:label path="firstName">
				<spring:message code="user.register_form.first_name"/>
				<form:input path="firstName" type="text" minlength="1" required="required"/>
			</form:label> <form:errors path="firstName"/>
			<br/>
			<form:label path="lastName">
				<spring:message code="user.register_form.last_name"/>
				<form:input path="lastName" type="text" minlength="1" required="required"/>
			</form:label> <form:errors path="lastName"/>
			<br/>
			<form:label path="email">
				<spring:message code="user.register_form.email"/>
				<form:input path="email" type="email" minlength="3" required="required"/>
			</form:label> <form:errors path="email"/>
			<br/>
			<form:label path="password">
				<spring:message code="user.register_form.password"/>
				<form:input path="password" type="password" minlength="4" required="required"/>
			</form:label> <form:errors path="password"/>
			<br/>
			
			<form:label for="role-input-user" path="userRoleDto">
				<spring:message code="user.register_form.role.user"/>
			</form:label>
			<form:errors path="userRoleDto"/>
			<input id="role-input-user" name="userRoleDto" type="radio" value="USER" required="required"/>
			
			<form:label for="role-input-manager" path="userRoleDto">
				<spring:message code="user.register_form.role.manager"/>
			</form:label>
			<form:errors path="userRoleDto"/>
			<input id="role-input-manager" name="userRoleDto" type="radio" value="MANAGER" required="required"/>
			
			<form:label for="role-input-admin" path="userRoleDto">
				<spring:message code="user.register_form.role.admin"/>
			</form:label>
			<form:errors path="userRoleDto"/>
			<input id="role-input-admin" name="userRoleDto" type="radio" value="ADMIN" required="required"/>
			<br/>
			<form:button><spring:message code="user.register_form.button_register"/></form:button>
		</form:form>
	</div>
</body>
</html>