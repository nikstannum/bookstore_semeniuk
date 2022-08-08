package com.belhard.service;

import com.belhard.dao.entity.Book;
import com.belhard.service.dto.BookDto;
import java.util.List;

public interface BookService {
    BookDto create(BookDto bookDto);

    BookDto get(long id);

    List<BookDto> getAll();

    BookDto getBookDtoByIsbn(String isbn);

    List<BookDto> getBooksByAuthor(String author);

    BookDto update(BookDto bookDto);

    boolean delete(long id);

    int countAllBooks();
}
