package com.belhard.dao;

import com.belhard.dao.entity.Book;

import java.util.List;

public interface BookDao extends CrudDao<Long, Book> {
    Book getBookByIsbn(String isbn);

    List<Book> getBooksByAuthor(String author);
}
