package com.belhard.service;

import com.belhard.service.dto.BookDto;
import java.math.BigDecimal;
import java.util.List;

public interface BookService {//FIXME extract AbstractService
    BookDto create(BookDto bookDto);

    BookDto getById(long id);

    List<BookDto> getAll();

    BookDto getBookDtoByIsbn(String isbn);

    List<BookDto> getBooksByAuthor(String author);

    BookDto update(BookDto bookDto);

    void delete(long id);

    int countAll();

    BigDecimal totalCostAllBooksOfAuthor(String author);
}
