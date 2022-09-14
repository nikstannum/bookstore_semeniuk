<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/jsp/navbar.jsp"></jsp:include>

<body>
<jsp:include page="/jsp/paging.jsp"/>
	<div>
		<h5>
			<a href="controller?command=create_book_form">Create new book</a>
		</h5>
	</div>
	<div>
		<h5>
			${requestScope.message} <br /> Our books:
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
  
	<table >
		<tr>
			<th>#</th>
			<th>title</th>
			<th>author</th>
			<th>add to cart</th>
			<th>action update</th>
		</tr>
		<c:forEach items="${books}" var="book" varStatus="counter">
			<tr>
				<td>${counter.count}</td>
				<td><a href="controller?command=book&id=${book.id}">${book.title}</a></td>
				<td>${book.author}</td>
				<td>
					<form method="post" action="controller">
						<input type="hidden" name="command" value="add_to_cart" /> 
						<input type="hidden" name="bookId" value="${book.id}" />
						<input type="hidden" name="currentCommand" value="${requestScope.currentCommand}" />
						<input type="hidden" name="currentPage" value="${requestScope.currentPage}" />
						<input type="submit" value="Add to cart" />
					</form>
				</td>
				<td><a href="controller?command=update_book_form&id=${book.id}"><input
						type="submit" value="UPDATE" /></a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
