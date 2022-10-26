package com.belhard.service;

import java.math.BigDecimal;
import java.util.List;

import com.belhard.service.dto.BookDto;

public interface BookService extends CrudService<Long, BookDto> {

	BookDto getBookDtoByIsbn(String isbn);

	List<BookDto> getBooksByAuthor(String author);

	BigDecimal totalCostAllBooksOfAuthor(String author);
}
