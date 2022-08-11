package main.java.com.belhard.service.impl;

import main.java.com.belhard.dao.BookDao;
import main.java.com.belhard.dao.entity.Book;
import main.java.com.belhard.service.BookService;
import main.java.com.belhard.service.dto.BookDto;
import java.math.BigDecimal;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = toEntity(bookDto);
        return toDto(bookDao.create(book));
    }

    private Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPages(bookDto.getPages());
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    public BookDto get(long id) {
        Book book = bookDao.get(id);
        return toDto(book);
    }

    private BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPages(book.getPages());
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    @Override
    public List<BookDto> getAll() {
        return bookDao.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public BookDto getBookDtoByIsbn(String isbn) {
        Book book = bookDao.getBookByIsbn(isbn);
        return toDto(book);
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        return bookDao.getBooksByAuthor(author).stream().map(this::toDto).toList();
    }


    @Override
    public BookDto update(BookDto bookDto) {
        Book book = toEntity(bookDto);
        return toDto(bookDao.update(book));
    }

    @Override
    public boolean delete(long id) {
        return bookDao.delete(id);
    }

    @Override
    public int countAllBooks() {
        return bookDao.countAllBooks();
    }

    @Override
    public BigDecimal totalCostAllBooksOfAuthor(String author) {
        List<BookDto> dtos = getBooksByAuthor(author);
        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (BookDto dto : dtos) {
            totalCost = totalCost.add(dto.getPrice());
        }
        return totalCost;
    }
}
