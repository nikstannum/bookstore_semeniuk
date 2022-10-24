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
	<a href="?page=1"><button name="button">First</button></a>
	<a href="?page=${requestScope.currentPage - 1}"><button
			name="button">Prev</button></a>
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
	</c:if>
<c:if test="${requestScope.currentPage == 1}">
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
		<a href="?page=${requestScope.currentPage + 1}"><button
			name="button">Next</button></a>
	<a href="?page=${requestScope.totalPages}"><button name="button">Last</button></a>
</c:if>
<c:if
	test="${requestScope.currentPage > 1 && requestScope.currentPage < requestScope.totalPages}">
	<a href="?page=1"><button name="button">First</button></a>
	<a href="?page=${requestScope.currentPage - 1}"><button
			name="button">Prev</button></a>
		Page ${requestScope.currentPage} out of ${requestScope.totalPages}
		<a href="?page=${requestScope.currentPage + 1}"><button
			name="button">Next</button></a>
	<a href="?page=${requestScope.totalPages}"><button name="button">Last</button></a>
</c:if>
</head>
<form method="get" action="?page">
	<input type="hidden" /> 
	<label for="number-of-page">Go to page number</label>
	<input id="number-of-page" name="page" type="number" required="required" value="${requestScope.currentPage}" />
</form>
</html>