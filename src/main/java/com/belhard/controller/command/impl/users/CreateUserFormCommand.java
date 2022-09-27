package com.belhard.controller.command.impl.users;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CreateUserFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/user/createUserForm.jsp";
	}
}
