<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
	<h1>Login</h1>
	<h3>${requestScope.message}</h3>
	<form method="post" action="login">
		<input type="hidden"/>
		<label for="email-input">Email: </label>
		<input id="email-input" name="email" type="email" required="required"/>
		<br/>
		<label for="password-input">Password: </label>
		<input id="password-input" name="password" type="password" minlength="4" required="required"/>
		<br/>
		<input type ="submit" value="LOGIN"/>

	</form>

</body>
</html>