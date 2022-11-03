<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../navbar.jsp"/>
	<script src="/js/jQuery-3.6.1.js"></script>
	<script src="/js/books.js" defer></script>
</head>
<body>
	<jsp:include page="../paging.jsp" />
	<div>
		<h5>
			<a href="create_book_form"><spring:message code="books.create_new_book" /></a>
		</h5>
	</div>
	<div>
		<h5>
			${requestScope.message} <br />
			<spring:message code="books.our_books" />
		</h5>
	</div>
	<table>
		<thead>
			<tr>
				<th><spring:message code="books.sequence_number" /></th>
				<th><spring:message code="book.table.header.title" /></th>
				<th><spring:message code="book.table.header.author" /></th>
				<th><spring:message code="books.add_book_to_cart" /></th>
				<th><spring:message code="books.update_book" /></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</body>
</html>
