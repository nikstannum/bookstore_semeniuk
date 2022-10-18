package com.belhard.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;
import com.belhard.serviceutil.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final Mapper mapper;

	@Override
	@LogInvocation
	public BookDto create(BookDto bookDto) {
		validateCreate(bookDto);
		Book book = bookRepository.save(mapper.bookToEntity(bookDto));
		return mapper.bookToDto(book);
	}

	private void validateCreate(BookDto bookDto) {
		Book existing = bookRepository.getBookByIsbn(bookDto.getIsbn()); // TODO or Optional?
		if (existing != null) {
			throw new RuntimeException("Book with ISBN = " + bookDto.getIsbn() + " already exists");
		}
		validateNumberPages(bookDto);
	}

	private void validateUpdate(BookDto bookDto) {
		Book existing = bookRepository.getBookByIsbn(bookDto.getIsbn()); // TODO or Optional?
		if (existing == null) {
			throw new RuntimeException("Book with ISBN = " + bookDto.getIsbn() + " wasn't found");
		}
		validateNumberPages(bookDto);
	}

	private void validateNumberPages(BookDto bookDto) {
		if (bookDto.getPages() == null) {
			throw new RuntimeException("Pages couldn't be null");
		}
		if (bookDto.getPages() <= 0) {
			throw new RuntimeException("Pages must be greater than 0");
		}
	}

	@Override
	public BookDto get(Long id) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		Book book = optionalBook.orElseThrow(() -> new RuntimeException("Book with id = " + id + " doesn't exist"));
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
	public BookDto getBookDtoByIsbn(String isbn) {
		Book book = bookRepository.getBookByIsbn(isbn); // TODO or Optional?
		if (book == null) {
			throw new RuntimeException("Couldn't find book with isbn: " + isbn);
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
	public void delete(Long id) {
		Book book = bookRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("book with id = " + id + " wasn't find"));
		book.setDeleted(true);
		bookRepository.save(book);
	}

	@LogInvocation
	@Override
	public long countAll() { // TODO Check work on @Where deleted = false
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
