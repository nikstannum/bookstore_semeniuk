package com.belhard.controller.command.impl.books;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class CreateBookFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/book/createBookForm.jsp";
	}
}
