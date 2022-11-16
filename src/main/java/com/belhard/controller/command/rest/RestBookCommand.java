package com.belhard.controller.command.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.belhard.aop.LogInvocation;
import com.belhard.exception.ValidationException;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/books")
public class RestBookCommand {
	private final BookService bookService;

	@LogInvocation
	@GetMapping("/{id}")
	public BookDto getById(@PathVariable Long id) {
		return bookService.get(id);
	}

	@LogInvocation
	@GetMapping()
	public Page<BookDto> getAll(Pageable pageable) {
		return bookService.getAll(pageable);
	}

	@ModelAttribute
	public BookDto book() {
		BookDto book = new BookDto();
		return book;
	}

	@PostMapping()
	@LogInvocation
	public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto book, Errors errors) {
		checkErrors(errors);
		BookDto created = bookService.create(book);
		return buildResponseCreated(created);
	}

	@LogInvocation
	@PutMapping("/{id}")
	public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid BookDto book, Errors errors) {
		checkErrors(errors);
		book.setId(id);
		return bookService.update(book);
	}

	@PatchMapping("/{id}")
	@LogInvocation
	@ResponseStatus(HttpStatus.ACCEPTED)
	public BookDto updatePart(@PathVariable Long id, @RequestBody @Valid BookDto book) { // FIXME not used anywhere
		book.setId(id);
		return bookService.update(book);
	}

	@DeleteMapping("/{id}")
	@LogInvocation
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		bookService.delete(id);
	}

	private void checkErrors(Errors errors) {
		if (errors.hasErrors()) {
			throw new ValidationException(errors);
		}
	}

	private ResponseEntity<BookDto> buildResponseCreated(BookDto created) {
		ResponseEntity<BookDto> responseEntity = ResponseEntity.status(HttpStatus.CREATED)
						.location(getLocation(created))
						.body(created);
		return responseEntity;
	}

	private URI getLocation(BookDto book) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/books/{id}").buildAndExpand(book.getId())
						.toUri();
	}
}
