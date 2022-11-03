<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../navbar.jsp"></jsp:include>
	<script src="/js/jQuery-3.6.1.js"></script>
	<script src="/js/users.js" defer></script>
</head>
<body>
	<jsp:include page="../paging.jsp"/>
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
