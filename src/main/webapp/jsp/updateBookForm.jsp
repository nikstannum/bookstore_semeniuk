<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update existing book</title>
</head>
<body>
	<h1>Update existing book</h1>
	<form method="post" action="controller">
		<input name="command" type="hidden" value="update_book"/>
		<input name="id" type="hidden" value="${requestScope.book.id}"/>
		<label for="title-input">Title: </label>
		<input id="title-input" name="title" type="text" value="${requestScope.book.title}" minlength="1"/>
		<br/>
		<label for="author-input">Author: </label>
		<input id="author-input" name="author" type="text" value="${requestScope.book.author}" minlength="1"/>
		<br/>
		<label for="isbn-input">isbn: </label>
		<input id="isbn-input" name="isbn" type="text" value="${requestScope.book.isbn}"minlength="1"/>
		<br/>
		<label for="pages-input">Pages: </label>
		<input id="pages-input" name="pages" type="number" value="${requestScope.book.pages}" minlength="1"/>
		<br/>
		<label for="price-input">Price: </label>
		<input id="price-input" name="price" type="text" value="${requestScope.book.price}" minlength="1"/>
		<br />
		<label for="cover-input-soft">Soft</label>
		<input id="cover-input-soft" name="cover" type="radio" value="SOFT" />
		<label for="cover-input-hard">Hard </label>
		<input id="cover-input-hard" name="cover" type="radio" value="HARD" />
		<label for="cover-input-special">Special </label>
		<input id="cover-input-special" name="cover" type="radio" value="SPECIAL" />
		<br/>
		<input type ="submit" value="UPDATE"/>

	</form>

</body>
</html>