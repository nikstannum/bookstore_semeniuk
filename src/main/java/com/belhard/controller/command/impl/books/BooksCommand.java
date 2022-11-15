package com.belhard.controller.command.impl.books;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belhard.aop.LogInvocation;
import com.belhard.exception.MyAppException;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("books")
@RequiredArgsConstructor
public class BooksCommand {
	private final BookService bookService;
	private final MessageSource messageSource;

	@LogInvocation
	@GetMapping("/{id}")
	public String bookById(@PathVariable Long id, Model model) {
		BookDto dto = bookService.get(id);
		model.addAttribute("book", dto);
		String message = messageSource.getMessage("general.result.of_search", null, LocaleContextHolder.getLocale());
		model.addAttribute("message", message);
		return "book/book";
	}

	@LogInvocation
	@GetMapping()
	public String allBooks() {
		return "book/books_js";
	}

	@LogInvocation
	@GetMapping("/create_book_form")
	public String createBookForm() {
		return "book/createBookForm_js";
	}

	@ModelAttribute
	public BookDto book() {
		BookDto book = new BookDto();
		return book;
	}

	@GetMapping("/{id}/update")
	@LogInvocation
	public String updateBookForm(@PathVariable Long id, Model model) {
		BookDto book = bookService.get(id);
		model.addAttribute("book", book);
		return "book/updateBookForm_js";
	}

//	@ExceptionHandler
//	public String myAppExc(MyAppException e, Model model) {
//		model.addAttribute("message", e.getMessage());
//		return "error";
//	}

}
