<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
<script defer src="/js/jQuery-3.6.1.js"></script>
 <style>
   TABLE {
    width: 100%; 
    border-collapse: collapse; 
   }
   TD, TH {
    padding: 3px; 
    border: 1px solid black;
   }
   TH {
    background: #b0e0e6;
   }
  </style>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<jsp:include page="../paging.jsp"/>
	<table >
		<tr>
			<th>#</th>
			<th><spring:message code="orders.table.header.order_id"/></th>
			<th><spring:message code="orders.table.header.user_id"/></th>
			<th><spring:message code="orders.table.header.status"/></th>
			<th><spring:message code="orders.table.header.total_cost"/></th>
		</tr>
		<c:forEach items="${orders}" var="order" varStatus="counter">
			<tr>
				<td>${counter.count}</td>
				<td><a href="${order.id}">${order.id}</a></td>
				<td>${order.userDto.id}</td>
				<td>${order.statusDto}</td>
				<td>${order.totalCost}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
