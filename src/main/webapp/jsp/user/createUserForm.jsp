<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/jsp/navbar.jsp"></jsp:include>
</head>
<body>
	<h1>Register new user</h1>
	<form method="post" action="controller">
		<input name="command" type="hidden" value="create_user"/>
		<label for="first-name-input">First name: </label>
		<input id="first-name-input" name="firstName" type="text" minlength="1" required="required"/>
		<br/>
		<label for="last-name-input">Last name: </label>
		<input id="last-name-input" name="lastName" type="text" minlength="1" required="required"/>
		<br/>
		<label for="email-input">Email: </label>
		<input id="email-input" name="email" type="email" required="required"/>
		<br/>
		<label for="password-input">Password: </label>
		<input id="password-input" name="password" type="password" minlength="4" required="required"/>
		<br/>
		<label for="role-input-user">User</label>
		<input id="role-input-user" name="role" type="radio" value="USER" required="required"/>
		<label for="role-input-manager">Manager</label>
		<input id="role-input-manager" name="role" type="radio" value="MANAGER" required="required"/>
		<label for="role-input-admin">Admin</label>
		<input id="role-input-admin" name="role" type="radio" value="ADMIN" required="required"/>
		<br/>
		<input type ="submit" value="REGISTER"/>

	</form>

</body>
</html>