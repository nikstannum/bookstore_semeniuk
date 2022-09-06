<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register new user</title>
</head>
<body>
	<h1>Register new user</h1>
	<form method="post" action="controller">
		<input name="command" type="hidden" value="create_user"/>
		<label for="email-input">Email: </label>
		<input id="email-input" name="email" type="email"/>
		<br/>
		<label for="password-input">Password: </label>
		<input id="password-input" name="password" type="password"/>
		<br/>
		<input type ="submit" value="REGISTER"/>

	</form>

</body>
</html>