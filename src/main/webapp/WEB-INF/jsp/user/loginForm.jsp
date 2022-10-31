<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
	<h1><spring:message code="user.message.login"/></h1>
	<h3>${requestScope.message}</h3>
	<form method="post" action="login">
		<input type="hidden"/>
		<label for="email-input" path="email"><spring:message code="user.login_form.email"/></label> 
		<input id="email-input" path="email" minlength="1" type="email" required="required"/> 
		<br/>
		<label for="password-input" path="password"><spring:message code="user.login_form.password"/> </label> 
		<input id="password-input" path="password" type="password" minlength="4" required="required"/>
		<br/>
		<input type ="submit" value="<spring:message code="user.login_form.button_login"/>"/>

	</form>

</body>
</html>