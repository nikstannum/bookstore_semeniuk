package com.belhard.dao;

import java.util.List;

import com.belhard.dao.entity.Book;

public interface BookRepository extends CrudRepository<Long, Book> {
	Book getBookByIsbn(String isbn);

	List<Book> getBooksByAuthor(String author);
}
