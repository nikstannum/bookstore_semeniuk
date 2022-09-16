<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="/jsp/navbar.jsp"></jsp:include>
</head>
<body>
	<h5>${requestScope.message}</h5>
	<table style="width: 100%">
		<tr>
			<th>order id</th>
			<th>user</th>
			<th>items</th>
			<th>action</th>
			<th>status</th>
		</tr>

		<tr>
			<td>${order.id}</td>
			<td>${order.userDto.email}</td>
			<td>
				<c:forEach items="${order.detailsDto}" var="detailsDto">
					<a href="controller?command=book&id=${detailsDto.bookDto.id}">${detailsDto.bookDto.title}</a>
					<br />
					<br />
				</c:forEach>
			</td>
			
			
			<td>
				<c:forEach items="${order.detailsDto}" var="detailsDto">
					<a href="controller?command=decrease_quantity&orderId=${order.id}&detailsDtoId=${detailsDto.id}"><button name="button">-</button></a>
				${detailsDto.bookQuantity}
					<a href="controller?command=increase_quantity&orderId=${order.id}&detailsDtoId=${detailsDto.id}"><button name="button">+</button></a>
					<br/> <br/>
				</c:forEach>
				</td>
				
				
			<td>${order.statusDto}</td>
			<td>
				<form method="post" action="controller">
					<input type="hidden" name="command" value="delete_from_cart" /> <input
						type="hidden" name="bookId" value="${detailsDto.bookDto.id}" /> <input
						type="submit" value="Delete from cart" />
				</form>
			</td>
		</tr>
	</table>
	<div>total cost = ${cart.totalCost}</div>
	<div>
		<form method="post" action="controller">
			<input type="hidden" name="command" value="update_order" /> <input
				type="hidden" name="cart" value="${cart}" /> <input type="submit"
				value="Add book DON'T WORK" />
		</form>
	</div>
	<br />
	<div>
		<form method="post" action="controller">
			<input type="hidden" name="command" value="update_order" /> <input
				type="hidden" name="orderId" value="${order.id}" /> <input type="submit"
				value="Update order" />
		</form>
	</div>
</body>
</html>
