<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="navbar.jsp"></jsp:include>
<body>
	<div>
		<c:if test="${sessionScope.user != null}">
			<spring:message code="main.welcome"/>, ${user.firstName}!
		</c:if>
		<c:if test="${sessionScope.user == null}">
			<spring:message code="main.welcome"/>, guest!
		</c:if>
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

