<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<script src="/js/jQuery-3.6.1.js"></script>
	<script src="/js/users.js" type="module"></script>
	
	<meta name="query-string" content="${pageContext.request.queryString}">
	
	<sec:authorize access="hasAnyAuthority('ADMIN', 'MANAGER')">
		<meta name="ourRoleAdminOrManager" content="true">
	</sec:authorize>
	<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
		<div class="pagination">
		<button class="first"><spring:message code="paging.first_page"/></button>
		<button class="prev"><spring:message code="paging.previous_page"/></button>
		<button class="current"></button>
		<button class="next"><spring:message code="paging.next_page"/></button>
		<button class="last"><spring:message code="paging.last_page"/></button>
	</div>
		<table >
			<thead>
				<tr>
					<th>first name</th>
					<th>last name</th>
					<th>email</th>
					<th>action</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
</body>
</html>

