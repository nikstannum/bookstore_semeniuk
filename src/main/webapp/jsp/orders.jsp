<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="navbar.jsp"></jsp:include>
</head>
<body>
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
	<table >
		<tr>
			<th>#</th>
			<th>order id</th>
			<th>user id</th>
			<th>status</th>
			<th>total cost</th>
		</tr>
		<c:forEach items="${orders}" var="order" varStatus="counter">
			<tr>
				<td>${counter.count}</td>
				<td><a href="controller?command=order&id=${order.id}">${order.id}</a></td>
				<td>${order.userDto.id}</td>
				<td>${order.statusDto}</td>
				<td>${order.totalCost}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
