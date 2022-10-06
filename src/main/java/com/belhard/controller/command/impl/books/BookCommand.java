package com.belhard.controller.command.impl.books;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.BookDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BookCommand implements Command {

	private final BookService service;

	public BookCommand(BookService service) {
		this.service = service;
	}

	@Override
	public String execute(HttpServletRequest req) {
		Long id = getId(req);
		BookDto dto = service.get(id);
		req.setAttribute("book", dto);
		req.setAttribute("message", "result of search:");
		return "jsp/book/book.jsp";
	}

	private Long getId(HttpServletRequest req) {
		String idStr = req.getParameter("id");
		long id;
		id = Long.parseLong(idStr);
		return id;
	}
}
