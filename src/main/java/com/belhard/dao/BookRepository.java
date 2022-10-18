package com.belhard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belhard.dao.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book getBookByIsbn(String isbn);

	List<Book> getBooksByAuthor(String author);
}
