<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<c:if test="${requestScope.currentPage == requestScope.totalPages && requestScope.currentPage != 1}">
		<a href="?page=1"><button class="first-page"><spring:message code="paging.first_page"/></button></a>
		<a href="?page=${requestScope.currentPage - 1}">
			<button class="prev-page"><spring:message code="paging.previous_page"/></button></a>
		<spring:message code="paging.current_page"/> ${requestScope.currentPage} <spring:message code="paging.total_page"/> ${requestScope.totalPages}
	</c:if>
	
	<c:if test="${requestScope.currentPage == 1 && requestScope.totalPages > 1}">
				<spring:message code="paging.current_page"/> ${requestScope.currentPage} <spring:message code="paging.total_page"/> ${requestScope.totalPages}
			<a href="?page=${requestScope.currentPage + 1}"><button
				class="next-page"><spring:message code="paging.next_page"/></button></a>
		<a href="?page=${requestScope.totalPages}"><button class="last-page"><spring:message code="paging.last_page"/></button></a>
	</c:if>
	<c:if
		test="${requestScope.currentPage > 1 && requestScope.currentPage < requestScope.totalPages}">
		<a href="?page=1"><button class="first-page"><spring:message code="paging.first_page"/></button></a>
		<a href="?page=${requestScope.currentPage - 1}"><button
				class="prev-page"><spring:message code="paging.previous_page"/></button></a>
			<spring:message code="paging.current_page"/> ${requestScope.currentPage} out of ${requestScope.totalPages}
			<a href="?page=${requestScope.currentPage + 1}"><button
				class="next-page"><spring:message code="paging.next_page"/></button></a>
		<a href="?page=${requestScope.totalPages}"><button class="last-page"><spring:message code="paging.last_page"/></button></a>
	</c:if>
</head>
<form method="get" action="?page">
	<input type="hidden" /> 
	<label for="number-of-page"><spring:message code="paging.go_to_page"/></label>
	<input id="number-of-page" name="page" type="number" required="required" value="${requestScope.currentPage}" />
</form>
</html>