package com.belhard.controller.command.impl.books;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.exception.MyAppException;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import lombok.RequiredArgsConstructor;

@Controller
//@RequestMapping("books")
@RequiredArgsConstructor
public class BooksCommand {
	private final BookService service;
	private final PagingUtil pagingUtil;
	private final MessageSource messageSource;

	@LogInvocation
	@GetMapping("books/all")
	public String allBooks(@RequestParam(defaultValue = "10") Integer limit,
					@RequestParam(defaultValue = "1") Long page, Model model) {
		Paging paging = pagingUtil.getPaging(limit, page);
		List<BookDto> books = service.getAll(paging);
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		model.addAttribute("books", books);
		model.addAttribute("currentCommand", "books/all");
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", paging.getPage());
		return "book/books";
	}
	
	@LogInvocation
	@GetMapping("api/books/all")
	public String allBooksRest() {
		return "book/books_js";
	}

	@LogInvocation
	@GetMapping("books/{id}")
	public String bookById(@PathVariable Long id, Model model) {
		BookDto dto = service.get(id);
		model.addAttribute("book", dto);
		String message = messageSource.getMessage("general.result.of_search", null, LocaleContextHolder.getLocale());
		model.addAttribute("message", message);
		return "book/book";
	}

	@LogInvocation
	@GetMapping("books/create_book_form")
	public String createBookForm() {
		return "book/createBookForm";
	}

	@ModelAttribute
	public BookDto book() {
		BookDto book = new BookDto();
		return book;
	}

	@PostMapping("books/create_book")
	@ResponseStatus(HttpStatus.CREATED)
	@LogInvocation
	public String createBook(@ModelAttribute BookDto book, Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("errors", errors.getFieldErrors());
			return "book/createBookForm";
		}
		BookDto created = service.create(book);
		model.addAttribute("book", created);
		String message = messageSource.getMessage("book.create.success", null, LocaleContextHolder.getLocale());
		model.addAttribute("message", message);
		return "book/book";
	}

	@GetMapping("books/update")
	@LogInvocation
	public String updateBookForm(@RequestParam Long id, Model model) {
		BookDto book = service.get(id);
		model.addAttribute("book", book);
		return "book/updateBookForm";
	}

	@LogInvocation
	@PostMapping("books/update_book")
	public String updateBook(@ModelAttribute BookDto dto, Model model) {
		BookDto updated = service.update(dto);
		model.addAttribute("book", updated);
		String message = messageSource.getMessage("book.update.success", null, LocaleContextHolder.getLocale());
		model.addAttribute("message", message);
		return "book/book";
	}

	@ExceptionHandler
	public String myAppExc(MyAppException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
	}

}
