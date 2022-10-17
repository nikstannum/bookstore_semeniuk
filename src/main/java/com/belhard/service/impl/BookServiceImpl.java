package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookRepository;
import com.belhard.dao.entity.Book;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.serviceutil.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookDao;
	private final Mapper mapper;

	@Override
	@LogInvocation
	public BookDto create(BookDto bookDto) {
		validateCreate(bookDto);
		Book book = toEntity(bookDto);
		return toDto(bookDao.create(book));
	}

	@LogInvocation
	private void validateCreate(BookDto bookDto) {
		Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
		if (existing != null) {
			throw new RuntimeException("Book with ISBN = " + bookDto.getIsbn() + " already exists");
		}
		validate(bookDto);
	}

	@LogInvocation
	private static void validate(BookDto bookDto) {
		if (bookDto.getPages() == null) {
			throw new RuntimeException("Pages couldn't be null");
		}
		if (bookDto.getPages() <= 0) {
			throw new RuntimeException("Pages must be greater than 0");
		}
	}

	@LogInvocation
	private void validateUpdate(BookDto bookDto) {
		Book existing = bookDao.getBookByIsbn(bookDto.getIsbn());
		if (existing != null && !(existing.getId().equals(bookDto.getId()))) {
			throw new RuntimeException("Book with ISBN = " + bookDto.getIsbn() + " already exists");
		}
		validate(bookDto);
	}

	@LogInvocation
	public Book toEntity(BookDto bookDto) {
		Book book = mapper.bookToEntity(bookDto);
		return book;
	}

	@LogInvocation
	@Override
	public BookDto get(Long id) {
		Book book = bookDao.get(id);
		if (book == null) {
			throw new RuntimeException("Couldn't find book with id: " + id);
		}
		return toDto(book);
	}

	@LogInvocation
	public BookDto toDto(Book book) {
		BookDto bookDto = mapper.bookToDto(book);
		return bookDto;
	}

	@LogInvocation
	@Override
	public List<BookDto> getAll() {
		return bookDao.getAll().stream().map(this::toDto).toList();
	}

	@Override
	@LogInvocation
	public List<BookDto> getAll(Paging paging) {
		int limit = paging.getLimit();
		long offset = paging.getOffset();
		return bookDao.getAll(limit, offset).stream().map(this::toDto).toList();
	}

	@LogInvocation
	@Override
	public BookDto getBookDtoByIsbn(String isbn) {
		Book book = bookDao.getBookByIsbn(isbn);
		if (book == null) {
			throw new RuntimeException("Couldn't find book with isbn: " + isbn);
		}
		return toDto(book);
	}

	@LogInvocation
	@Override
	public List<BookDto> getBooksByAuthor(String author) {
		return bookDao.getBooksByAuthor(author).stream().map(this::toDto).toList();
	}

	@LogInvocation
	@Override
	public BookDto update(BookDto bookDto) {
		validateUpdate(bookDto);
		Book book = toEntity(bookDto);
		return toDto(bookDao.update(book));
	}

	@LogInvocation
	@Override
	public void delete(Long id) {
		if (!bookDao.delete(id)) {
			throw new RuntimeException("Couldn't delete object with id = " + id);
		}
	}

	@LogInvocation
	@Override
	public long countAll() {
		return bookDao.countAll();
	}

	@LogInvocation
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
