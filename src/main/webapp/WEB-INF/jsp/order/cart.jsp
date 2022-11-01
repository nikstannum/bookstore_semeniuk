<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
	<h5>${requestScope.message}</h5>
	<c:if test="${requestScope.cart != null}">
	<table style="width: 100%">
		<tr>
			<th>#</th>
			<th><spring:message code="cart.table.header.title"/></th>
			<th><spring:message code="cart.table.header.author"/></th>
			<th><spring:message code="cart.table.header.price"/></th>
			<th><spring:message code="cart.table.header.quantity"/></th>
			<th><spring:message code="cart.table.header.action"/></th>
		</tr>

			<c:forEach items="${cart.detailsDto}" var="detailsDto" varStatus="counter">
		<tr>
				<td>${counter.count}</td>
				<td><a href="../books/${detailsDto.bookDto.id}">${detailsDto.bookDto.title}</a></td>
				<td>${detailsDto.bookDto.author}</td>
				<td>${detailsDto.bookPrice}</td>
				<td>${detailsDto.bookQuantity}</td>
				<td>
					<form method="post" action="controller">
						<input type="hidden" name="command" value="delete_from_cart" /> <input
							type="hidden" name="bookId" value="${detailsDto.bookDto.id}" /> <input
							type="submit" value="<spring:message code="cart.table.button_delete"/>" />
					</form>
				</td>
		</tr>
			</c:forEach>
	</table>
	<div><spring:message code="cart.total_cost"/>${cart.totalCost}</div>
	<div>
		<form method="post" action="checkout">
						<input type="hidden" name="command" value="checkout_order" /> <input
							type="hidden" name="cart" value="${cart}" /> <input
							type="submit" value="<spring:message code="cart.button_checkout"/>" />
					</form>
	</div>
	</c:if>
</body>
</html>
