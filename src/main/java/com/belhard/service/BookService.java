package com.belhard.service;

import com.belhard.service.dto.BookDto;
import java.math.BigDecimal;
import java.util.List;

public interface BookService extends CrudService<Long, BookDto> {

    BookDto getBookDtoByIsbn(String isbn);

    List<BookDto> getBooksByAuthor(String author);

    BigDecimal totalCostAllBooksOfAuthor(String author);
}
