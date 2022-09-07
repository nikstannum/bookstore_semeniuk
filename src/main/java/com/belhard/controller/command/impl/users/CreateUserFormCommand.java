package com.belhard.controller.command.impl.users;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class CreateUserFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/createUserForm.jsp";
	}
}
