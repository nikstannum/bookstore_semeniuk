package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.dao.BookRepository;
import com.belhard.dao.entity.Book;
import com.belhard.exception.EntityNotFoundException;
import com.belhard.exception.SuchEntityExistsException;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.serviceutil.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final Mapper mapper;
	private final MessageSource messageSource;

	@Override
	@LogInvocation
	public BookDto create(BookDto bookDto) {
		validateCreate(bookDto);
		Book book = bookRepository.save(mapper.bookToEntity(bookDto));
		return mapper.bookToDto(book);
	}

	private void validateCreate(BookDto bookDto) throws SuchEntityExistsException {
		Book existing = bookRepository.getBookByIsbn(bookDto.getIsbn());
		if (existing != null) {
			Object[] obj = new Object[] { bookDto.getIsbn() };
			String message = messageSource.getMessage("book.error.book_with_isbn_already_exists", obj,
							LocaleContextHolder.getLocale());
			throw new SuchEntityExistsException(message);
		}
	}

	private void validateUpdate(BookDto bookDto) throws EntityNotFoundException {
		Book existing = bookRepository.getBookByIsbn(bookDto.getIsbn());
		if (existing == null) {
			Object[] obj = new Object[] { bookDto.getIsbn() };
			String message = messageSource.getMessage("book.error.book_with_isbn_wasn't_found", obj,
							LocaleContextHolder.getLocale());
			throw new EntityNotFoundException(message);
		}
	}

	@LogInvocation
	@Override
	public BookDto get(Long id) throws EntityNotFoundException {
		Optional<Book> optionalBook = bookRepository.findById(id);
		Book book = optionalBook.orElseThrow(() -> {
			String message = messageSource.getMessage("book.book_not_found", null, LocaleContextHolder.getLocale());
			throw new EntityNotFoundException(message);
		});
		return mapper.bookToDto(book);
	}

	@LogInvocation
	@Override
	public List<BookDto> getAll() {
		List<Book> list = bookRepository.findAll();
		return list.stream().map(mapper::bookToDto).toList();
	}

	@Override
	@LogInvocation
	public List<BookDto> getAll(Paging paging) {
		int page = (int) paging.getPage();
		int limit = paging.getLimit();
		Sort sort = Sort.by(Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		Page<Book> booksPage = bookRepository.findAll(pageable);
		return booksPage.toList().stream().map(mapper::bookToDto).toList();
	}

	@LogInvocation
	@Override
	public BookDto getBookDtoByIsbn(String isbn) throws EntityNotFoundException {
		Book book = bookRepository.getBookByIsbn(isbn);
		if (book == null) {
			Object[] obj = new Object[] { isbn };
			String message = messageSource.getMessage("book.error.book_with_isbn_wasn't_found", obj,
							LocaleContextHolder.getLocale());
			throw new EntityNotFoundException(message);
		}
		return mapper.bookToDto(book);
	}

	@LogInvocation
	@Override
	public List<BookDto> getBooksByAuthor(String author) {
		return bookRepository.getBooksByAuthor(author).stream().map(mapper::bookToDto).toList();
	}

	@LogInvocation
	@Override
	public BookDto update(BookDto bookDto) {
		validateUpdate(bookDto);
		Book book = mapper.bookToEntity(bookDto);
		Book updated = bookRepository.save(book);
		return mapper.bookToDto(updated);
	}

	@LogInvocation
	@Override
	public void delete(Long id) throws EntityNotFoundException {
		String message = messageSource.getMessage("book.book_not_found", null, LocaleContextHolder.getLocale());
		Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
		book.setDeleted(true);
		bookRepository.save(book);
	}

	@LogInvocation
	@Override
	public long countAll() {
		return bookRepository.count();
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
