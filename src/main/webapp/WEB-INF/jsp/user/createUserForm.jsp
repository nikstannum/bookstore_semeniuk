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
	<h1>Register new user</h1>
	<div>
		<form:form method="post" action="create_user" modelAttribute="userDto">
			<input type="hidden" />
			<form:label path="firstName">First name: <form:input path="firstName" type="text" minlength="1" required="required"/></form:label>
			<br/>
			<form:label path="lastName">Last name: <form:input path="lastName" type="text" minlength="1" required="required"/></form:label>
			<br/>
			<form:label path="email">Email: <form:input path="email" type="email" minlength="3" required="required"/></form:label> <form:errors path="email"/>
			<br/>
			<form:label path="password">Password: <form:input path="password" type="password" minlength="4" required="required"/></form:label> <form:errors path="password"/>
			<br/>
			<form:label for="role-input-user" path="userRoleDto">User</form:label>
			<input id="role-input-user" name="userRoleDto" type="radio" value="USER" required="required"/>
			<form:label for="role-input-manager" path="userRoleDto">Manager</form:label>
			<input id="role-input-manager" name="userRoleDto" type="radio" value="MANAGER" required="required"/>
			<form:label for="role-input-admin" path="userRoleDto">Admin</form:label>
			<input id="role-input-admin" name="userRoleDto" type="radio" value="ADMIN" required="required"/>
			<br/>
			<form:button>REGISTER</form:button>
		</form:form>
	</div>
</body>
</html>