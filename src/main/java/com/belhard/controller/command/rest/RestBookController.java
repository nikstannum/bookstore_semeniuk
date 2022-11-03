package com.belhard.controller.command.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.belhard.aop.LogInvocation;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/books")
public class RestBookController {
	private final BookService service;

	@ResponseStatus(HttpStatus.OK)
	@LogInvocation
	@GetMapping()
	public List<BookDto> allBooks() {
		List<BookDto> books = service.getAll();
		return books;
	}

	@ResponseStatus(HttpStatus.OK)
	@LogInvocation
	@GetMapping("/{id}")
	public BookDto getById(@PathVariable Long id) {
		return service.get(id);
	}

	@ModelAttribute
	public BookDto book() {
		BookDto book = new BookDto();
		return book;
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@LogInvocation
	public BookDto createBook(@RequestBody @Valid BookDto book, Errors errors) { // FIXME to do processing errors
		return service.create(book);
	}

	@LogInvocation
	@PutMapping()
	public BookDto updateBook(@RequestBody BookDto dto) {
		return service.update(dto);
	}

}
