package com.belhard.controller.command.impl.users;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class UpdateUserFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		req.setAttribute("id", req.getParameter("id"));
		return "jsp/updateUserForm.jsp";
	}
}
