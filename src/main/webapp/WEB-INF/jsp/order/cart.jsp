<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<th>title</th>
			<th>author</th>
			<th>price</th>
			<th>quantity</th>
			<th>delete from cart</th>
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
							type="submit" value="Delete from cart" />
					</form>
				</td>
		</tr>
			</c:forEach>
	</table>
	<div>total cost = ${cart.totalCost}</div>
	<div>
		<form method="post" action="checkout">
						<input type="hidden" name="command" value="checkout_order" /> <input
							type="hidden" name="cart" value="${cart}" /> <input
							type="submit" value="Checkout" />
					</form>
	</div>
	</c:if>
</body>
</html>
