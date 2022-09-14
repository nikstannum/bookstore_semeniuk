<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${requestScope.totalPages == 1}">
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
	</c:if>
	<c:if test="${requestScope.currentPage == requestScope.totalPages}">
		<a href="controller?command=${requestScope.currentCommand}&page=1"><button name="button">First</button></a>
		<a
			href="controller?command=${requestScope.currentCommand}&page=${requestScope.currentPage - 1}"><button
				name="button">Prev</button></a>
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
	</c:if>
	<c:if test="${requestScope.currentPage == 1}">
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
		<a
			href="controller?command=${requestScope.currentCommand}&page=${requestScope.currentPage + 1}"><button
				name="button">Next</button></a>
		<a href="controller?command=${requestScope.currentCommand}&page=${requestScope.totalPages}"><button
				name="button">Last</button></a>
	</c:if>
	<c:if
		test="${requestScope.currentPage > 1 && requestScope.currentPage < requestScope.totalPages}">
		<a href="controller?command=${requestScope.currentCommand}&page=1"><button name="button">First</button></a>
		<a
			href="controller?command=${requestScope.currentCommand}&page=${requestScope.currentPage - 1}"><button
				name="button">Prev</button></a>
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
		<a
			href="controller?command=${requestScope.currentCommand}&page=${requestScope.currentPage + 1}"><button
				name="button">Next</button></a>
		<a href="controller?command=${requestScope.currentCommand}&page=${requestScope.totalPages}"><button
				name="button">Last</button></a>
	</c:if>
</head>
<form method="get" action="controller">
	<input name="command" type="hidden" value="${requestScope.currentCommand}" /> <label
		for="number-of-page">Go to page number</label> <input
		id="number-of-page" name="page" type="number" minlength="1"
		required="required" value="${requestScope.currentPage}" />
</form>
</html>