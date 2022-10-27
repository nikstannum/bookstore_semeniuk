<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../navbar.jsp"></jsp:include>
</head>
<body>
	<h1>Add new book</h1>
	<form method="post" action="create_book">
		<input type="hidden" />
		<label	for="title-input">Title: </label> <input id="title-input" name="title" type="text" required="required" minlength="1"/>
		<br />
		<label for="author-input">Author: </label>
		<input id="author-input" name="author" type="text" required="required" minlength="1"/>
		<br />
		<label for="isbn-input">isbn: </label>
		<input id="isbn-input" name="isbn"	type="text" required="required" minlength="1"/>
		<br />
		<label for="pages-input">Pages: </label>
		<input id="pages-input" name="pages" type="number" required="required" minlength="1"/>
		<br />
		<label for="price-input">Price: </label>
		<input id="price-input" name="price" type="text" required="required" minlength="1"/>
		<br />
		<label for="cover-input-soft">Soft</label>
		<input id="cover-input-soft" name="coverDto" type="radio" value="SOFT" required="required"/>
		<label for="cover-input-hard">Hard </label>
		<input id="cover-input-hard" name="coverDto" type="radio" value="HARD" required="required"/>
		<label for="cover-input-special">Special </label>
		<input id="cover-input-special" name="coverDto" type="radio" value="SPECIAL" required="required"/>
		<br />
		<input type="submit" value="REGISTER" />
	</form>

</body>
</html>