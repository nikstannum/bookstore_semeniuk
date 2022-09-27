package com.belhard.controller.command.impl;

import org.springframework.stereotype.Controller;

import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginFormCommand implements Command {

	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/loginForm.jsp";
	}

}
