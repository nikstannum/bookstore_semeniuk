package com.belhard.dao;

import com.belhard.dao.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Long, Book> {
    Book getBookByIsbn(String isbn);

    List<Book> getBooksByAuthor(String author);
}
