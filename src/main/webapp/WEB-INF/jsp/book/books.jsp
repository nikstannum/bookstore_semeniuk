<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../navbar.jsp"/>
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

	<table>
		<tr>
			<th><spring:message code="books.sequence_number" /></th>
			<th><spring:message code="book.table.header.title" /></th>
			<th><spring:message code="book.table.header.author" /></th>
			<th><spring:message code="books.add_book_to_cart" /></th>
			<th><spring:message code="books.update_book" /></th>
		</tr>
		<c:forEach items="${books}" var="book" varStatus="counter">
			<tr>
				<td>${counter.count}</td>
				<td><a href="${book.id}">${book.title}</a></td>
				<td>${book.author}</td>
				<td>
					<form method="post" action="../add_to_cart">
						<input type="hidden"/>
						<input type="hidden" name="bookId" value="${book.id}" />
						<input type="hidden" name="currentCommand" value="${requestScope.currentCommand}" />
						<input type="hidden" name="currentPage" value="${requestScope.currentPage}" />
						<input type="submit" value="<spring:message code="books.button.add_to_cart" />" />
					</form>
				</td>
				<td><a href="update?id=${book.id}"><input
						type="submit" value="<spring:message code="books.button.update" />" /></a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
