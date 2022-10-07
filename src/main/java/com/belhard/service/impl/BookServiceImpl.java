package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookRepository;
import com.belhard.dao.entity.Book;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.serviceutil.Mapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BookServiceImpl implements BookService {
	private final BookRepository bookDao;

	@Autowired
	public BookServiceImpl(BookRepository bookDao) {
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
	public List<BookDto> getAll(Paging paging) {
		int limit = paging.getLimit();
		long offset = paging.getOffset();
		return bookDao.getAll(limit, offset).stream().map(this::toDto).toList();
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
	public long countAll() {
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
