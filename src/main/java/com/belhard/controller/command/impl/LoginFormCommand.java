package com.belhard.controller.command.impl;

import org.springframework.stereotype.Controller;

import com.belhard.aop.LogInvocation;
import com.belhard.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginFormCommand implements Command {

	@LogInvocation
	@Override
	public String execute(HttpServletRequest req) {
		return "jsp/loginForm.jsp";
	}

}
