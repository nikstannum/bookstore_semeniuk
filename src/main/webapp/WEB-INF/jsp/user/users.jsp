<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<jsp:include page="../paging.jsp"/>
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
			<th>first name</th>
			<th>last name</th>
			<th>email</th>
			<th>action</th>
		</tr>
		<c:forEach items="${users}" var="user" varStatus="counter">
			<tr>
				<td>${counter.count}</td>
				<td><a href="${user.id}"><c:out value="${user.firstName}"/></a></td>
				<td><c:out value="${user.lastName}"/></td>
				<td><c:out value="${user.email}"/></td>
				<td><a href="update?id=${user.id}"><input type ="submit" value="UPDATE"/></a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
