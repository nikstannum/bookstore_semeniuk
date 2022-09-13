package com.belhard.controller.command.impl.books;

import com.belhard.controller.command.Command;
import com.belhard.dao.entity.Book;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import jakarta.servlet.http.HttpServletRequest;

public class UpdateBookFormCommand implements Command {
	private final BookService service;
	

	public UpdateBookFormCommand(BookService service) {
		this.service = service;
	}


	@Override
	public String execute(HttpServletRequest req) {
		Long id = Long.parseLong(req.getParameter("id"));
		BookDto book = service.get(id);
		req.setAttribute("book", book);
		return "jsp/book/updateBookForm.jsp";
	}

}
