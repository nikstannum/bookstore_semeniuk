<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="query-string" content="${pageContext.request.queryString}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}">
	<meta name="_csrf_token" content="${_csrf.token}">
	<script defer src="/js/jQuery-3.6.1.js"></script>
	<script type="module" src="/js/books.js" defer></script>
</head>
<body>
	<jsp:include page="../navbar.jsp"/>
	<div class="pagination">
		<button class="first"><spring:message code="paging.first_page"/></button>
		<button class="prev"><spring:message code="paging.previous_page"/></button>
		<button class="current"></button>
		<button class="next"><spring:message code="paging.next_page"/></button>
		<button class="last"><spring:message code="paging.last_page"/></button>
	</div>
	<div>
		<h5>
			<a href="books/create_book_form"><spring:message code="books.create_new_book" /></a>
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
				<th><spring:message code="books.delete_book" /></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</body>
</html>
