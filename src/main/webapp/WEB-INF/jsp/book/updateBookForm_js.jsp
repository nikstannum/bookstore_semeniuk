<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<title>update book</title>
	<script src="/js/jQuery-3.6.1.js"></script>
	<script src="/js/updateBook.js" defer></script>
</head>
<body>
	<jsp:include page="../navbar.jsp"></jsp:include>
	<h1><spring:message code="book.message.update_form"/></h1>
	<form class="input-form">
		<input id="input-id" name="id" type="hidden" value="${requestScope.book.id}"/>
		<label for="input-title"><spring:message code="book.update_form.title"/> </label>
		<input id="input-title" name="title" type="text" value="${requestScope.book.title}" minlength="1" required="required"/>
		<br/>
		<label for="input-author"><spring:message code="book.update_form.author"/> </label>
		<input id="input-author" name="author" type="text" value="${requestScope.book.author}" minlength="1" required="required"/>
		<br/>
		<label for="input-isbn"><spring:message code="book.update_form.isbn"/> </label>
		<input id="input-isbn" name="isbn" type="text" value="${requestScope.book.isbn}"minlength="1" required="required"/>
		<br/>
		<label for="input-pages"><spring:message code="book.update_form.pages"/> </label>
		<input id="input-pages" name="pages" type="number" value="${requestScope.book.pages}" minlength="1" required="required"/>
		<br/>
		<label for="input-price"><spring:message code="book.update_form.price"/> </label>
		<input id="input-price" name="price" type="text" value="${requestScope.book.price}" minlength="1" required="required"/>
		<br/>
		<label>
			<input name="coverDto" type="radio" value="SOFT" required="required"/>
			<spring:message code="book.update_form.cover.soft"/>
		</label>
		<label>
			<input name="coverDto" type="radio" value="HARD" required="required"/>
			<spring:message code="book.update_form.cover.hard"/>
		</label>
		<label>
			<input name="coverDto" type="radio" value="SPECIAL" required="required"/>
			<spring:message code="book.update_form.cover.special"/>
		</label>
		<br/>
		<button class="update"><spring:message code="book.update_form.button.update"/></button>
	</form>
</body>
</html>