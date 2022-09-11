package com.belhard.service.impl;

import com.belhard.dao.BookDao;
import com.belhard.dao.entity.Book;
import com.belhard.dao.entity.Book.BookCover;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.service.dto.BookDto.BookCoverDto;
import com.belhard.util.Mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookServiceImpl implements BookService {
	private final BookDao bookDao;
	private static final Logger log = LogManager.getLogger(BookServiceImpl.class);

	public BookServiceImpl(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public BookDto create(BookDto bookDto) {
		log.debug("Service method called successfully");
		validateCreate(bookDto);
		Book book = toEntity(bookDto);
		return toDto(bookDao.create(book));
	}

	private void validateCreate(BookDto bookDto) {
		Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
		if (existing != null) {
			throw new RuntimeException("Book with ISBN = " + bookDto.getIsbn() + " already exists");
		}
		validate(bookDto);
	}

	private static void validate(BookDto bookDto) {
		if (bookDto.getPages() == null) {
			throw new RuntimeException("Pages couldn't be null");
		}
		if (bookDto.getPages() <= 0) {
			throw new RuntimeException("Pages must be greater than 0");
		}
	}

	private void validateUpdate(BookDto bookDto) {
		Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
		if (existing != null && !(existing.getId().equals(bookDto.getId()))) {
			throw new RuntimeException("Book with ISBN = " + bookDto.getIsbn() + " already exists");
		}
		validate(bookDto);
	}

	public Book toEntity(BookDto bookDto) {
		Book book = Mapper.INSTANCE.bookToEntity(bookDto);
		return book;
	}

	@Override
	public BookDto get(Long id) {
		log.debug("Service method called successfully");
		Book book = bookDao.get(id);
		if (book == null) {
			throw new RuntimeException("Couldn't find book with id: " + id);
		}
		return toDto(book);
	}

	public BookDto toDto(Book book) {
		BookDto bookDto = Mapper.INSTANCE.bookToDto(book);
		return bookDto;
	}

	@Override
	public List<BookDto> getAll() {
		log.debug("Service method called successfully");
		return bookDao.getAll().stream().map(this::toDto).toList();
	}

	@Override
	public BookDto getBookDtoByIsbn(String isbn) {
		log.debug("Service method called successfully");
		Book book = bookDao.getBookByIsbn(isbn);
		if (book == null) {
			throw new RuntimeException("Couldn't find book with isbn: " + isbn);
		}
		return toDto(book);
	}

	@Override
	public List<BookDto> getBooksByAuthor(String author) {
		log.debug("Service method called successfully");
		return bookDao.getBooksByAuthor(author).stream().map(this::toDto).toList();
	}

	@Override
	public BookDto update(BookDto bookDto) {
		log.debug("Service method called successfully");
		validateUpdate(bookDto);
		Book book = toEntity(bookDto);
		return toDto(bookDao.update(book));
	}

	@Override
	public void delete(Long id) {
		log.debug("Service method called successfully");
		if (!bookDao.delete(id)) {
			throw new RuntimeException("Couldn't delete object with id = " + id);
		}
	}

	@Override
	public int countAll() {
		log.debug("Service method called successfully");
		return bookDao.countAll();
	}

	@Override
	public BigDecimal totalCostAllBooksOfAuthor(String author) {
		log.debug("Service method called successfully");
		List<BookDto> dtos = getBooksByAuthor(author);
		BigDecimal totalCost = BigDecimal.valueOf(0);
		for (BookDto dto : dtos) {
			totalCost = totalCost.add(dto.getPrice());
		}
		return totalCost;
	}
}
