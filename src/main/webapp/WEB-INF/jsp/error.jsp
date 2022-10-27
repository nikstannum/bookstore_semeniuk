<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="navbar.jsp"></jsp:include>
</head>
<body>
	<h1><spring:message code="error.error"/></h1>
	<h3><spring:message code="error.something_went_wrong"/>.</h3>
	<h4>${requestScope.message}</h4>
</body>
</html>

