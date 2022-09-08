package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class LoginFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/loginForm.jsp";
	}

}
