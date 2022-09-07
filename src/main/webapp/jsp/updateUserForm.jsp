<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update existing user</title>
</head>
<body>
	<h1>Update existing user</h1>
	<form method="post" action="controller">
		<input name="command" type="hidden" value="update_user"/>
		<input name="id" type="hidden" value="${requestScope.id}"/>
		<label for="first-name-input">First name: </label>
		<input id="first-name-input" name="firstName" type="text" minlength="1"/>
		<br/>
		<label for="last-name-input">Last name: </label>
		<input id="last-name-input" name="lastName" type="text" minlength="1"/>
		<br/>
		<label for="email-input">Email: </label>
		<input id="email-input" name="email" type="email"/>
		<br/>
		<label for="password-input">Password: </label>
		<input id="password-input" name="password" type="password" minlength="4"/>
		<br/>
		<label for="role-input-user">User</label>
		<input id="role-input-user" name="role" type="radio" value="USER"/>
		<label for="role-input-manager">Manager</label>
		<input id="role-input-manager" name="role" type="radio" value="MANAGER"/>
		<label for="role-input-admin">Admin</label>
		<input id="role-input-admin" name="role" type="radio" value="ADMIN"/>
		<br/>
		<input type ="submit" value="UPDATE"/>

	</form>

</body>
</html>