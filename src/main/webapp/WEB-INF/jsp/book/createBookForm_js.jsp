<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<title>create book</title>
	<script src="/js/jQuery-3.6.1.js"></script>
	<script src="/js/createBook.js" defer></script>
</head>
<body>
	<jsp:include page="../navbar.jsp"></jsp:include>
	<h1><spring:message code="book.message.create_book"/></h1>
	<div>
		<form class="input-form">
		    <label for="input-title"><spring:message code="book.create_form.title"/></label>
		    <input id="input-title" type="text" name="title" minlength="1" required="required"/>
		    <br/>
		    <label for="input-author"><spring:message code="book.create_form.author"/></label>
		    <input id="input-author" type="text" name="author" minlength="1" required="required"/>
		    <br/>
		    <label for="input-isbn"><spring:message code="book.create_form.isbn"/></label>
		    <input id="input-isbn" type="text" name="isbn" minlength="1" required="required"/>
		    <br/>
		    <label for="input-pages"><spring:message code="book.create_form.pages"/></label>
		    <input id="input-pages" type="number" name="pages" minlength="1" required="required"/>
		    <br/>
		    <label for="input-price"><spring:message code="book.create_form.price"/></label>
		    <input id="input-price" type="text" name="price" minlength="1" required="required"/>
		    <br/>
		    <label>
				<input name="coverDto" type="radio" value="SOFT" required="required"/>
				<spring:message code="book.create_form.cover.soft"/>
			</label>
			<label>
				<input name="coverDto" type="radio" value="HARD" required="required"/>
				<spring:message code="book.create_form.cover.hard"/>
			</label>
			<label>
				<input name="coverDto" type="radio" value="SPECIAL" required="required"/>			
				<spring:message code="book.create_form.cover.special"/>
			</label>
			<br/>
		    <button class="create"><spring:message code="book.create_form.button.add"/></button>
		</form>
	</div>
</body>
</html>
