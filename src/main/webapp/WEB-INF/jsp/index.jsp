<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">
<head>
<script defer src="/js/jQuery-3.6.1.js"></script>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div>
		<sec:authorize access="isAuthenticated()">
			<spring:message code="main.welcome"/>, <sec:authentication property="principal.username"/>
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<spring:message code="main.welcome"/>, guest!
		</sec:authorize>
	</div>
	<div class="container text-center my-5">
		<div class="row">
			<div class="col-lg-10 col-md-4 mx-auto">
				<img class="rounded-img" src="/images/android-chrome-512x512.png"
					alt="firstImg" />
				<h1 class="fw-light">Bookstore</h1>
				<p class="lead text-muted">We are glad to see you</p>
			</div>
		</div>
	</div>
</body>
</html>

