<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script defer src="/js/jQuery-3.6.1.js"></script>
<style>
.rounded-img {
	width: 60%;
	border-radius: 50%;
	box-shadow: 0 0 10px rgba(45, 9, 9, 50);
	padding: 0.3em;
	margin-bottom: 1em;
}
</style>
<style>
.rounded-img {
	width: 40%;
	border-radius: 0%;
	box-shadow: 0 0 5px rgba(45, 9, 9, 50);
	padding: 0.1em;
	margin-bottom: 1em;
}
</style>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous" />
<link rel="apple-touch-icon" sizes="180x180"
	href="images/apple-touch-icon.png" />

<link rel="icon" type="image/png" sizes="16x16"
	href="/images/favicon-16x16.png" />
<link rel="manifest" href="images/site.webmanifest" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js"
	integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK"
	crossorigin="anonymous"></script>
</head>
<body>
	<jsp:include page="../navbar.jsp"></jsp:include>
	<h4>${requestScope.message}</h4>
	<p>
		<spring:message code="user.message.personal_info" />
	</p>

	<table style="width: 100%">
		<tr>
			<th><spring:message code="user.table.header.id" /></th>
			<th><spring:message code="user.table.header.first_name" /></th>
			<th><spring:message code="user.table.header.last_name" /></th>
			<th><spring:message code="user.table.header.email" /></th>
			<th><spring:message code="user.table.header.role" /></th>
		</tr>
		<tr>
			<td>${user.id}</td>
			<td><c:out value="${user.firstName}" /></td>
			<td><c:out value="${user.lastName}" /></td>
			<td><c:out value="${user.email}" /></td>
			<td>${user.userRoleDto}</td>
		</tr>
	</table>
	<div class="container text-center my-5">
		<div class="row">
			<div class="col-lg-10 col-md-4 mx-auto">
				<img class="rounded-img" src="/images/pes.jpg" alt="smartDog" />
			</div>
		</div>
	</div>
</body>
</html>

