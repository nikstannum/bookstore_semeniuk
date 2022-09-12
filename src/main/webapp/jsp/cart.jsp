<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Cart</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous" />
<link rel="apple-touch-icon" sizes="180x180"
	href="images/apple-touch-icon.png" />
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png" />
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png" />
<link rel="manifest" href="images/site.webmanifest" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.min.js"
	integrity="sha384-ODmDIVzN+pFdexxHEHFBQH3/9/vQ9uori45z4JjnFsRydbmQbmL5t1tQ0culUzyK"
	crossorigin="anonymous"></script>
<style>
.rounded-img {
	width: 60%;
	border-radius: 50%;
	box-shadow: 0 0 10px rgba(45, 9, 9, 50);
	padding: 0.3em;
	margin-bottom: 1em;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand p-0" href="images/favicon-32x32.png"><img
				src="images/favicon-32x32.png" alt="icon" width="40" /></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="/bookstore_semeniuk/">Home</a></li>
					<li class="nav-item"><a class="nav-link"
						href="controller?command=books">All books</a></li>
					<li class="nav-item"><a class="nav-link"
						href="controller?command=users">All users</a></li>
					<li class="nav-item"><a class="nav-link"
						href="controller?command=orders">All orders</a></li>
					<li class="nav-item"><a class="nav-link active" href="#">Cart</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}
</style>
</head>
<body>
	</div>
	<h5>${cart.userDto.email}, ${requestScope.message}</h5>
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
				<td><a href="controller?command=book&id=${detailsDto.bookDto.id}">${detailsDto.bookDto.title}</a></td>
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
		<form method="post" action="controller">
						<input type="hidden" name="command" value="checkout_order" /> <input
							type="hidden" name="cart" value="${cart}" /> <input
							type="submit" value="Checkout" />
					</form>
	</div>
</body>
</html>
