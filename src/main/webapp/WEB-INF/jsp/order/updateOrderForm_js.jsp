<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
	<script src="/js/jQuery-3.6.1.js"></script>
	<script src="/js/updateOrderForm.js" defer></script>
</head>
<body>
	<h5>${requestScope.message}</h5>
	<table style="width: 100%">
		<thead>
		<tr>
			<th><spring:message code="order.table.header.order_id"/></th>
			<th><spring:message code="order.table.header.user"/></th>
			<th><spring:message code="order.table.header.items"/></th>
			<th><spring:message code="order.table.header.action"/></th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>${order.id}</td>
			<td>${order.userDto.email}</td>
			<td>
				<c:forEach items="${order.detailsDto}" var="detailsDto">
					<a href="../books/${detailsDto.bookDto.id}">${detailsDto.bookDto.title}</a>
					<br />
					<br />
				</c:forEach>
			</td>
			<td id="buttoms">
			</td>
		</tr>
		</tbody>
	</table>
	<br/>
	<div><spring:message code="order.message.total_cost"/>${order.totalCost}</div>
	<br/>
	<div>
					<form method="post" action="update_order">
					<input type="hidden" name="orderId" value="${order.id}" />
					
					<label for="status-input-canceled"><spring:message code="order.status.canceled"/></label>
					<input id="status-input-canceled" name="statusDto" type="radio" value="CANCELED" required="required" />
					<label for="status-input-pending"><spring:message code="order.status.pending"/> </label>
					<input id="status-input-pending" name="statusDto" type="radio" value="PENDING" required="required" />
					<label for="status-input-paid"><spring:message code="order.status.paid"/> </label>
					<input id="status-input-paid" name="statusDto" type="radio" value="PAID" required="required" />
					<label for="status-input-delivered"><spring:message code="order.status.delivered"/> </label>
					<input id="status-input-delivered" name="statusDto" type="radio" value="DELIVERED" required="required" /><br/><br/>
					<input type="submit" value="<spring:message code="order.button.update_order"/>" />
				</form>
		</div>
	<br />
</body>
</html>
