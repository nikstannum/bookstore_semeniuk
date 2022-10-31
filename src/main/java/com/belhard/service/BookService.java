package com.belhard.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.belhard.service.dto.BookDto;

public interface BookService extends CrudService<Long, BookDto> {

	BookDto getBookDtoByIsbn(String isbn, Locale locale);

	List<BookDto> getBooksByAuthor(String author, Locale locale);

	BigDecimal totalCostAllBooksOfAuthor(String author, Locale locale);

}
