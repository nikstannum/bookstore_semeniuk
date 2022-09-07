package com.belhard.controller.command.impl.books;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import jakarta.servlet.http.HttpServletRequest;

public class BooksCommand implements Command {
	private static final Logger log = LogManager.getLogger(BooksCommand.class);
	private final BookService service;

	public BooksCommand(BookService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		List<BookDto> books = service.getAll();
		req.setAttribute("books", books);
		log.info("return page jsp/books.jsp");
		return "jsp/books.jsp";
	}
}
