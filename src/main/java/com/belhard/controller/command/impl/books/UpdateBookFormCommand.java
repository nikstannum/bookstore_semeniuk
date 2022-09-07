package com.belhard.controller.command.impl.books;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class UpdateBookFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		req.setAttribute("id", req.getParameter("id"));
		return "jsp/updateBookForm.jsp";
	}

}
