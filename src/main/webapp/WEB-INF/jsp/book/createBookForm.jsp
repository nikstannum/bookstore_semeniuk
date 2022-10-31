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
	<h1><spring:message code="book.message.create_book"/></h1>
	<div>
		<form:form method="post" action="create_book" modelAttribute="bookDto">
			<input type="hidden" />
			<form:label path="title">
				<spring:message code="book.create_form.title"/>
				<form:input path="title" type="text" minlength="1" required="required"/>
			</form:label> <form:errors path="title"/>
			<br/>
			<form:label path="author">
				<spring:message code="book.create_form.author"/>
				<form:input path="author" type="text" minlength="1" required="required"/>
			</form:label> <form:errors path="author"/>
			<br/>
			<form:label path="isbn">
				<spring:message code="book.create_form.isbn"/>
				<form:input path="isbn" type="text" minlength="1" required="required"/>
			</form:label> <form:errors path="isbn"/>
			<br/>
			<form:label path="pages">
				<spring:message code="book.create_form.pages"/>
				<form:input path="pages" type="number" minlength="1" required="required"/>
			</form:label> <form:errors path="pages"/>
			<br/>
			<form:label path="price">
				<spring:message code="book.create_form.price"/>
				<form:input path="price" type="text" required="required" minlength="1"/>
			</form:label> <form:errors path="price"/>
			<br />
			<form:label for="cover-input-soft" path="coverDto">
				<spring:message code="book.create_form.cover.soft"/>
			</form:label> <form:errors path="coverDto"/>
			<input id="cover-input-soft" name="coverDto" type="radio" value="SOFT" required="required"/>
			<form:label for="cover-input-hard" path="coverDto">
				<spring:message code="book.create_form.cover.hard"/>
			</form:label> <form:errors path="coverDto"/>
			<input id="cover-input-hard" name="coverDto" type="radio" value="HARD" required="required"/>
			<form:label for="cover-input-special" path="coverDto">
				<spring:message code="book.create_form.cover.special"/>
			</form:label> <form:errors path="coverDto"/>
			<input id="cover-input-special" name="coverDto" type="radio" value="SPECIAL" required="required"/>			
			<br/>
			<form:button>
				<spring:message code="book.create_form.button.add"/>
			</form:button>
		</form:form>
	</div>
</body>
</html>
