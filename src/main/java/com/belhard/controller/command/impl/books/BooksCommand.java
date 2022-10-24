package com.belhard.controller.command.impl.books;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.util.PagingUtil;
import com.belhard.controller.util.PagingUtil.Paging;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

@Controller
@RequestMapping("books")
public class BooksCommand {
	private final BookService service;
	private final PagingUtil pagingUtil;

	public BooksCommand(BookService service, PagingUtil pagingUtil) {
		this.service = service;
		this.pagingUtil = pagingUtil;
	}

	@LogInvocation
	@RequestMapping("/all")
	public String allBooks(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "1") Long page,
					Model model) {
		Paging paging = pagingUtil.getPaging(limit, page);
		List<BookDto> books = service.getAll(paging);
		long totalEntities = service.countAll();
		long totalPages = pagingUtil.getTotalPages(totalEntities, paging.getLimit());
		model.addAttribute("books", books);
		model.addAttribute("currentCommand", "books");
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", paging.getPage());
		return "book/books";
	}

	@LogInvocation
	@RequestMapping("/{id}")
	public String bookById(@PathVariable Long id, Model model) {
		BookDto dto = service.get(id);
		model.addAttribute("book", dto);
		model.addAttribute("message", "result of search:");
		return "book/book";
	}

	@LogInvocation
	@RequestMapping("/create_book_form")
	public String createBookForm() {
		return "book/createBookForm";
	}

	@RequestMapping("/create_book")
	@ResponseStatus(HttpStatus.CREATED)
	@LogInvocation
	public String createBook(@ModelAttribute BookDto dto, Model model) {
		BookDto created = service.create(dto);
		model.addAttribute("book", created);
		String message = "book created successfully";
		model.addAttribute("message", message);
		return "book/book";
	}

	@RequestMapping("/update")
	@LogInvocation
	public String updateBookForm(@RequestParam Long id, Model model) {
		BookDto book = service.get(id);
		model.addAttribute("book", book);
		return "book/updateBookForm";
	}

	@LogInvocation
	@RequestMapping("/update_book")
	public String updateBook(@ModelAttribute BookDto dto, Model model) {
		BookDto updated = service.update(dto);
		model.addAttribute("book", updated);
		model.addAttribute("message", "book updated successfully");
		return "book/book";
	}
}
