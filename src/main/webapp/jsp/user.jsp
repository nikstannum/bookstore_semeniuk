<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="icon" type="image/png" sizes="32x32"
	href="images/favicon-32x32.png" />
<link rel="icon" type="image/png" sizes="16x16"
	href="images/favicon-16x16.png" />
<link rel="manifest" href="images/site.webmanifest" />
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
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
					<li class="nav-item"><a class="nav-link" href="controller?command=books">All
							books</a></li>
					<li class="nav-item"><a class="nav-link" href="controller?command=users">All
							users</a></li>
							<li class="nav-item"><a class="nav-link"
						href="controller?command=orders">All orders</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<h4>
	${requestScope.messageCreated}
	${requestScope.messageUpdated}
	</h4>
	<p>Personal info:</p>

	<table style="width: 100%">
		<tr>
			<th>id</th>
			<th>firstName</th>
			<th>lastName</th>
			<th>email</th>
			<th>role</th>
		</tr>
		<tr>
			<td>${user.id}</td>
			<td>${user.firstName}</td>
			<td>${user.lastName}</td>
			<td>${user.email}</td>
			<td>${user.userRoleDto}</td>
		</tr>
	</table>

	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
		crossorigin="anonymous" />
	<link rel="apple-touch-icon" sizes="180x180"
		href="images/apple-touch-icon.png" />

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
</body>
<div class="container text-center my-5">
	<div class="row">
		<div class="col-lg-10 col-md-4 mx-auto">
			<img class="rounded-img" src="images/pes.jpg" alt="smartDog" />
		</div>
	</div>
</div>
<style>
.rounded-img {
	width: 40%;
	border-radius: 0%;
	box-shadow: 0 0 5px rgba(45, 9, 9, 50);
	padding: 0.1em;
	margin-bottom: 1em;
}
</style>
</body>
</html>

