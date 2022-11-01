<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
	<h1><spring:message code="book.message.update_form"/></h1>
	<form method="post" action="update_book">
		<input type="hidden" />
		<input name="id" type="hidden" value="${requestScope.book.id}"/>
		<label for="title-input"><spring:message code="book.update_form.title"/> </label>
		<input id="title-input" name="title" type="text" value="${requestScope.book.title}" minlength="1" required="required"/>
		<br/>
		<label for="author-input"><spring:message code="book.update_form.author"/> </label>
		<input id="author-input" name="author" type="text" value="${requestScope.book.author}" minlength="1" required="required"/>
		<br/>
		<label for="isbn-input"><spring:message code="book.update_form.isbn"/> </label>
		<input id="isbn-input" name="isbn" type="text" value="${requestScope.book.isbn}"minlength="1" required="required"/>
		<br/>
		<label for="pages-input"><spring:message code="book.update_form.pages"/> </label>
		<input id="pages-input" name="pages" type="number" value="${requestScope.book.pages}" minlength="1" required="required"/>
		<br/>
		<label for="price-input"><spring:message code="book.update_form.price"/> </label>
		<input id="price-input" name="price" type="text" value="${requestScope.book.price}" minlength="1" required="required"/>
		<br />
		<label for="cover-input-soft"><spring:message code="book.update_form.cover.soft"/></label>
		<input id="cover-input-soft" name="coverDto" type="radio" value="SOFT" required="required"/>
		<label for="cover-input-hard"><spring:message code="book.update_form.cover.hard"/> </label>
		<input id="cover-input-hard" name="coverDto" type="radio" value="HARD" required="required"/>
		<label for="cover-input-special"><spring:message code="book.update_form.cover.special"/> </label>
		<input id="cover-input-special" name="coverDto" type="radio" value="SPECIAL" required="required"/>
		<br/>
		<input type ="submit" value="<spring:message code="book.cupdate_form.button.update"/>"/>

	</form>

</body>
</html>