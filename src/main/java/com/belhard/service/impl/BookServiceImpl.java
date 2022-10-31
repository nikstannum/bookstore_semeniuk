package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
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
import com.belhard.exception.WrongValueException;
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
	public BookDto create(BookDto bookDto, Locale locale) {
		validateCreate(bookDto, locale);
		Book book = bookRepository.save(mapper.bookToEntity(bookDto));
		return mapper.bookToDto(book);
	}

	private void validateCreate(BookDto bookDto, Locale locale) throws SuchEntityExistsException {
		Book existing = bookRepository.getBookByIsbn(bookDto.getIsbn());
		if (existing != null) {
			Object[] obj = new Object[] {bookDto.getIsbn()};
			String message = messageSource.getMessage("book.error.book_with_isbn_already_exists", obj, locale);
			throw new SuchEntityExistsException(message);
		}
	}

	private void validateUpdate(BookDto bookDto, Locale locale) throws EntityNotFoundException {
		Book existing = bookRepository.getBookByIsbn(bookDto.getIsbn());
		if (existing == null) {
			Object[] obj = new Object[] {bookDto.getIsbn()};
			String message = messageSource.getMessage("book.error.book_with_isbn_wasn't_found", obj, locale);
			throw new EntityNotFoundException(message);
		}
	}


	@LogInvocation
	@Override
	public BookDto get(Long id, Locale locale) throws EntityNotFoundException {
		Optional<Book> optionalBook = bookRepository.findById(id);
		Book book = optionalBook.orElseThrow(() -> {
			String message = messageSource.getMessage("book.book_not_found", null, locale);
			throw new EntityNotFoundException(message);
		});
		return mapper.bookToDto(book);
	}

	@LogInvocation
	@Override
	public List<BookDto> getAll(Locale locale) {
		List<Book> list = bookRepository.findAll();
		return list.stream().map(mapper::bookToDto).toList();
	}

	@Override
	@LogInvocation
	public List<BookDto> getAll(Paging paging, Locale locale) {
		int page = (int) paging.getPage();
		int limit = paging.getLimit();
		Sort sort = Sort.by(Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		Page<Book> booksPage = bookRepository.findAll(pageable);
		return booksPage.toList().stream().map(mapper::bookToDto).toList();
	}

	@LogInvocation
	@Override
	public BookDto getBookDtoByIsbn(String isbn, Locale locale) throws EntityNotFoundException {
		Book book = bookRepository.getBookByIsbn(isbn);
		if (book == null) {
			Object[] obj = new Object[] {isbn};
			String message = messageSource.getMessage("book.error.book_with_isbn_wasn't_found", obj, locale);
			throw new EntityNotFoundException(message);
		}
		return mapper.bookToDto(book);
	}

	@LogInvocation
	@Override
	public List<BookDto> getBooksByAuthor(String author, Locale locale) {
		return bookRepository.getBooksByAuthor(author).stream().map(mapper::bookToDto).toList();
	}

	@LogInvocation
	@Override
	public BookDto update(BookDto bookDto, Locale locale) {
		validateUpdate(bookDto, locale);
		Book book = mapper.bookToEntity(bookDto);
		Book updated = bookRepository.save(book);
		return mapper.bookToDto(updated);
	}

	@LogInvocation
	@Override
	public void delete(Long id, Locale locale) throws EntityNotFoundException {
		String message = messageSource.getMessage("book.book_not_found", null, locale);
		Book book = bookRepository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException(message));
		book.setDeleted(true);
		bookRepository.save(book);
	}

	@LogInvocation
	@Override
	public long countAll(Locale locale) {
		return bookRepository.count();
	}

	@LogInvocation
	@Override
	public BigDecimal totalCostAllBooksOfAuthor(String author, Locale locale) {
		List<BookDto> dtos = getBooksByAuthor(author, locale);
		BigDecimal totalCost = BigDecimal.valueOf(0);
		for (BookDto dto : dtos) {
			totalCost = totalCost.add(dto.getPrice());
		}
		return totalCost;
	}

}
