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
	<table style="width: 100%">
		<tr>
			<th>order id</th>
			<th>user</th>
			<th>items</th>
			<th>action</th>
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
					<a href="controller?command=decrease_quantity&orderId=${order.id}&detailsDtoId=${detailsDto.id}">
					<button	name="button">-</button></a>
							${detailsDto.bookQuantity}
					<a href="controller?command=increase_quantity&orderId=${order.id}&detailsDtoId=${detailsDto.id}">
					<button name="button">+</button></a>
					<br />
					<br />
				</c:forEach></td>
		</tr>
	</table>
	<br/>
	<div>total cost = ${order.totalCost}</div>
	<br/>
	<div>
					<form method="post" action="controller">
					<input type="hidden" name="orderId" value="${order.id}" />
					<input type="hidden" name="command" value="update_order" />
					<label for="status-input-canceled">CANCELED</label>
					<input id="status-input-canceled" name="status" type="radio" value="CANCELED" required="required" />
					<label for="status-input-pending">PENDING </label>
					<input id="status-input-pending" name="status" type="radio" value="PENDING" required="required" />
					<label for="status-input-paid">PAID </label>
					<input id="status-input-paid" name="status" type="radio" value="PAID" required="required" />
					<label for="status-input-delivered">DELIVERED </label>
					<input id="status-input-delivered" name="status" type="radio" value="DELIVERED" required="required" /><br/><br/>
					<input type="submit" value="Update order" />
				</form>
		</div>
	<br />
</body>
</html>
