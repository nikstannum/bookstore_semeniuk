package main.java.com.belhard.service;

import main.java.com.belhard.service.dto.BookDto;
import java.math.BigDecimal;
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

    BigDecimal totalCostAllBooksOfAuthor(String author);
}
